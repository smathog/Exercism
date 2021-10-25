#include "reverse_string.h"
#include <string>
#include <algorithm>

namespace reverse_string {
    //Allocating variant
    /*
    auto reverse_string(const std::string& str) -> std::string
    {
        return std::string(str.rbegin(), str.rend());
    }
     */

    //In-place variant
    auto reverse_string(std::string& str) -> std::string
    {
        for (auto frontIter = str.begin(), backIter = str.end(); frontIter < backIter; frontIter++, backIter--)
            std::iter_swap(frontIter, backIter);
        return str;
    }

    //Overload for rvalues
    auto reverse_string(std::string&& str) -> std::string
    {
        for (auto frontIter = str.begin(), backIter = str.end(); frontIter < backIter; frontIter++, backIter--)
            std::iter_swap(frontIter, backIter);
        return std::move(str);
    }
}  // namespace reverse_string
