from typing import Tuple

def middle_value(values: Tuple[int, int, int]) -> int:
    if values[0] > values[1]:
        if values[0] < values[2]:
            return values[0]

        if values[1] > values[2]:
            return values[1]

        return values[2]
    else:
        if values[0] > values[2]:
            return values[0]

        if values[1] < values[2]:
            return values[1]

        return values[2]
    
print(middle_value((7, 10, 5)))