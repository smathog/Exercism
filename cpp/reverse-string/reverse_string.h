#if !defined(REVERSE_STRING_H)
#define REVERSE_STRING_H

#include <string>

namespace reverse_string {
    //Allocating variant
    //auto reverse_string(const std::string& str) -> std::string;

    //In-place variant
    auto reverse_string(std::string& str) -> std::string;

    //Rvalue overload
    auto reverse_string(std::string&& str) -> std::string;

}  // namespace reverse_string

#endif // REVERSE_STRING_H