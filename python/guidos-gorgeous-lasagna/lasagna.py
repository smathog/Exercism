EXPECTED_BAKE_TIME: int = 40
PREP_TIME_PER_LAYER: int = 2


def bake_time_remaining(elapsed_bake_time: int) -> int:
    """Calculate the bake time remaining.

    :param elapsed_bake_time: int baking time already elapsed.
    :return: int remaining bake time derived from 'EXPECTED_BAKE_TIME'.

    Function that takes the actual minutes the lasagna has been in the oven as
    an argument and returns how many minutes the lasagna still needs to bake
    based on the `EXPECTED_BAKE_TIME`.
    """

    return EXPECTED_BAKE_TIME - elapsed_bake_time


def preparation_time_in_minutes(num_layers: int) -> int:
    """Calculate time to prepare num_layers layers

    :param num_layers: int number of layers to prepare
    :return: int total time to prepare given number of layers
    """
    return PREP_TIME_PER_LAYER * num_layers


def elapsed_time_in_minutes(num_layers: int, elapsed_bake_time: int) -> int:
    """

    :param num_layers: int number of layers in lasagna
    :param elapsed_bake_time: int number of time since baking began
    :return: int total time elapsed
    """
    return preparation_time_in_minutes(num_layers) + elapsed_bake_time
