This solution use a trick to solve this puzzle, see here:https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html, which directly test if the puzzle. Therefore, I have to write ugly code(use `toString()`) to use apply this method.

**Do not** checking the repeatability of the close board. It takes O(n) time to check the repeatability and slow down solving the puzzle.
