import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        System.out.println(solution(
                new int[]{2, 3},
                new int[]{1, 1},
                new int[]{1, 2},
                1));
    }

    public static int solution(int[] dimensions, int[] your_position, int[] trainer_position, int distance) {
        //Calculate grid dimensions
        int maxX = distance / dimensions[0] + 1;
        int maxY = distance / dimensions[1] + 1;

        //Get grid
        Room[][] grid = getRoomGrid(maxX, maxY, dimensions, your_position, trainer_position);

        //Get list of potential collision bodies:
        ArrayList<Room.Body> bodies = new ArrayList<>();
        for (int i = -maxX; i <= maxX; ++i) {
            for (int j = -maxY; j <= maxY; ++j) {
                //Only consider rooms potentially in range
                if (grid[maxX + i][maxY + j].getClosestDistanceSquared(your_position) <= distance * distance
                || (i == 0 && j == 0)) {
                    //Only add bodies in range to consideration
                    if (i != 0 || j != 0) {
                        if (getDistanceSquared(grid[maxX + i][maxY + j].getShooter().getCoords(), your_position) <=
                                distance * distance) {
                            bodies.add(grid[maxX + i][maxY + j].getShooter());
                        }
                    }
                    if (getDistanceSquared(grid[maxX + i][maxY + j].getTarget().getCoords(), your_position) <=
                            distance * distance) {
                        bodies.add(grid[maxX + i][maxY + j].getTarget());
                    }
                }
            }
        }

        //Sort bodies by distance from original shooter
        bodies.sort(Comparator.comparingInt(b -> getDistanceSquared(your_position, b.getCoords())));
        for (Solution.Room.Body body : bodies) {
            System.out.printf("Vector: %s Type: %s Distance2: %d Position: %s RoomOrientation: %s%n",
                    body.getVector(your_position),
                    body.type.toString(),
                    getDistanceSquared(your_position, body.getCoords()),
                    Arrays.toString(body.getCoords()),
                    body.getRoom().state.toString());
        }

        //blockingShooters is the set of vectors such that a shot from the original shooter would hit
        //the shooter (via reflection through the room grid) PRIOR to impacting a valid target reflection.
        HashSet<List<Integer>> blockingShooters = new HashSet<>();

        //validTargets is the set of vectors such that a shot from the original target is first hit and
        //absorbed by the target (or valid reflection of the target).
        HashSet<List<Integer>> validTargets = new HashSet<>();

        //bodies is sorted by ascending distance at this point, so to fill the two sets:
        //(NOTE: still restricting by distance here)
        //1) If a target is hit, and the vector is not present in either set, add it to the target vector set
        //2) If a shooter is hit, and the vector is not present in either set, add it to the target set
        //(Being present implies a shorter multiple of the vector was already hit, so the shot would be blocked!)
        for (Room.Body body : bodies) {
            if (getDistanceSquared(your_position, body.getCoords()) <= distance * distance) {
                List<Integer> vec = body.getVector(your_position);
                if (!blockingShooters.contains(vec)&& !validTargets.contains(vec)) {
                    if (body.getType() == Room.Body.Type.Shooter) {
                        blockingShooters.add(vec);
                    } else {
                        validTargets.add(vec);
                    }
                }
            }
        }

//        System.out.println("TARGETS: " );
//        for (List<Integer> vec : validTargets) {
//            System.out.println(vec);
//        }
//        System.out.println("BLOCKED SHOTS: ");
//        for (List<Integer> vec: blockingShooters) {
//            System.out.println(vec);
//        }

        return validTargets.size();
    }

    private static Room[][] getRoomGrid(int maxX,
                                        int maxY,
                                        int[] dimensions,
                                        int[] your_position,
                                        int[] trainer_position) {

        //Generate grid of Rooms
        Room[][] grid = new Room[2 * maxX + 1][];

        for (int i = -maxX; i <= maxX; ++i) {
            Room[] column = new Room[2 * maxY + 1];
            for (int j = -maxY; j <= maxY; ++j) {
                //Find the lower left corner for the room under construction
                int[] lowerLeftCorner = new int[] {
                        i * dimensions[0],
                        j * dimensions[1]
                };

                //Determine its reflection orientation
                Room.Reflection state;
                if (Math.abs(i) % 2 == 0 && Math.abs(j) % 2 == 0) {
                    state = Room.Reflection.None;
                } else if (Math.abs(i) % 2 == 1 && Math.abs(j) % 2 == 1) {
                    state = Room.Reflection.Both;
                } else if (Math.abs(i) % 2 == 1) {
                    state = Room.Reflection.Horizontal;
                } else { //Math.abs(j) % 2 == 1 only
                    state = Room.Reflection.Vertical;
                }

                //Construct the room
                column[maxY + j] = new Room(lowerLeftCorner,
                        dimensions,
                        your_position,
                        trainer_position,
                        state);
            }
            grid[maxX + i] = column;
        }
        return grid;
    }

    //Get the Euclidean distance squared from firstCoords to secondCoords
    private static int getDistanceSquared(int[] firstCoords, int[] secondCoords) {
        int deltaX = secondCoords[0] - firstCoords[0];
        int deltaY = secondCoords[1] - firstCoords[1];
        return deltaX * deltaX + deltaY * deltaY;
    }

    //Room inner class
    static class Room {
        //Indicates the orientation of the reflected room
        private final Reflection state;

        //Stores the coordinates (4 sets of 2-element arrays, each 2 element array is [x_pos, y_pos])
        //Order of corners is technically irrelevant in my usage, but by convention will be
        //  Lower left, upper left, upper right, lower right
        //It is important to note that the original is centered on it's lower-left corner at 0, 0
        //So all other coordinates can be described as transforms from 0, 0
        private final int[][] corners;

        private final Body shooter;
        private final Body target;

        Room(int[] lowerLeftCorner, int[] dimensions, int[] shooterPos, int[] targetPos, Reflection state) {
            this.state = state;
            corners = new int[4][];
            corners[0] = lowerLeftCorner;
            //upperLeftCorner
            corners[1] = new int[]{corners[0][0], corners[0][1] + dimensions[1]};
            //upperRightCorner
            corners[2] = new int[]{corners[0][0] + dimensions[0], corners[0][1] + dimensions[1]};
            //lowerRightCorner
            corners[3] = new int[]{corners[0][0] + dimensions[0], corners[0][1]};
            shooter = getBody(shooterPos, Body.Type.Shooter);
            target = getBody(targetPos, Body.Type.Target);
        }

        //Given the original pos for body, generate new body based on current room
        private Body getBody(int[] pos, Body.Type type) {
            Body temp;
            switch (state) {
                //Room is identical in space as the original, so just adjust coords
                case None: {
                    int[] adjustedPos = new int[]{pos[0] + corners[0][0], pos[1] + corners[0][1]};
                    temp = new Body(adjustedPos, type, this);
                    break;
                }

                //Room is flipped 180 about the Y axis, so the lower-left original is now the lower-right
                case Horizontal: {
                    int[] adjustedPos = new int[]{corners[3][0] - pos[0], corners[3][1] + pos[1]};
                    temp = new Body(adjustedPos, type, this);
                    break;
                }

                //Room is flipped 180 about the X axis, so the lower-left original is now the upper left
                case Vertical: {
                    int[] adjustedPos = new int[]{corners[1][0] + pos[0], corners[1][1] - pos[1]};
                    temp = new Body(adjustedPos, type, this);
                    break;
                }

                //Room is flipped 180 on both axes, so lower-left original is now the upper right
                case Both: {
                    int[] adjustedPos = new int[]{corners[2][0] - pos[0], corners[2][1] - pos[1]};
                    temp = new Body(adjustedPos, type, this);
                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + state);
            }
            return temp;
        }

        //Returns the closest distance from a corner of the room
        //(and thus the room overall) to coords
        private double getClosestDistanceSquared(int[] coords) {
            return Arrays.stream(corners)
                    .map(c -> getDistanceSquared(coords, c))
                    .min(Integer::compare)
                    .get();
        }

        public Body getShooter() {
            return shooter;
        }

        public Body getTarget() {
            return target;
        }

        //enum to indicate reflection/rotation state
        enum Reflection {
            None,
            Horizontal,
            Vertical,
            Both
        }

        //body class to represent target/shooter
        //different from other coords since can be collided with
        private static class Body {
            //x, y pair representing coordinates
            private final int[] coords;
            private final Type type;

            //reference to containing room, for debugging purposes only
            private final Room room;

            Body(int[] coords, Type type, Room room) {
                this.coords = coords;
                this.type = type;
                this.room = room;
            }

            //Gets a reduced (lowest term) vector from coords to this body
            public List<Integer> getVector(int[] coords) {
                int deltaX = this.coords[0] - coords[0];
                int deltaY = this.coords[1] - coords[1];
                int gcd = BigInteger.valueOf(deltaX).gcd(BigInteger.valueOf(deltaY)).intValueExact();
                deltaX /= gcd;
                deltaY /= gcd;
                return Arrays.asList(deltaX, deltaY);
            }

            public int[] getCoords() {
                return coords;
            }

            public Type getType() {
                return type;
            }

            public Room getRoom() {
                return room;
            }

            enum Type {
                Shooter,
                Target
            }
        }
    }
}