#include "leap.h"

namespace leap {
    auto is_leap_year(int year) -> bool
    {
        if (year % 4 == 0)
            if (year % 100 == 0 && year % 400 != 0)
                return false;
            else
                return true;
        else
            return false;
    }
}