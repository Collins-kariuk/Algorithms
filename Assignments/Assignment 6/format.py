import sys


def fun(input_file, L):
    """
    :param input_file: input file
    :param L: maximum line length
    :return: output file

    This function takes in an input file and a maximum line length and returns an output file with the minimum penalty
    for the entire sentence. The penalty is calculated as the cube of the difference between the maximum line length and
    the total length of the sentence. The function uses dynamic programming to calculate the minimum penalty for each
    word in the sentence and then iterates through the sentence to find the minimum penalty for the entire sentence.
    """

    # Read input file
    with open(input_file, 'r') as in_file:
        lines = in_file.readlines()

    # Split input into array of words
    inputString = ''.join(lines)

    # Split input into array of words
    arrayInput = inputString.split()

    # Create lookup table for the DP algorithm
    # the last element is 0 because there is no penalty for an empty string
    tableLookUp = [float('inf')] * (len(arrayInput) + 1)
    tableLookUp[-1] = 0

    # Create array to store break points which are the indices of the words that start a new line
    breakPoints = [0] * (len(arrayInput) + 1)

    # Iterate through the array of words in reverse order
    for i in range(len(arrayInput) - 1, -1, -1):
        # Initialize minimum penalty for current word to infinity
        minPenalty = float('inf')
        # Iterate forward from current word and calculate penalties
        for j in range(i, len(arrayInput)):
            # Calculate word length from i to j (inclusive)
            # Add 1 to account for spaces between words and subtract 1 to remove the last space
            wordLength = sum(len(word) + 1 for word in arrayInput[i:j+1]) - 1
            # Skip if total length exceeds limit
            if wordLength > L:
                continue
            # Calculate penalty for current concatenation of words
            penalty = (L - wordLength) ** 3
            # Check for minimum penalty considering previous penalties
            # Add penalty to next word's penalty
            minPenalty = min(minPenalty, tableLookUp[j+1] + penalty)
            # Store break point if minimum penalty is updated for current word
            if minPenalty == tableLookUp[j+1] + penalty:
                breakPoints[i] = j
        # Update lookup table with minimum penalty for current word
        tableLookUp[i] = minPenalty

    print(tableLookUp[0])

    # Final answer is the penalty for the entire sentence (first element)
    out_file_path = 'output.txt'

    # Write output to file by iterating through break points
    with open(out_file_path, 'w') as out_file:
        # First, write the penalty to the file
        out_file.write(str(tableLookUp[0]) + '\n')
        # Iterate through break points and write to file
        i = 0
        # Iterate through break points and write to file
        while i < len(arrayInput):
            # Write words from i to break point
            # Add 1 to break point to include the word at the break point
            # Join words with spaces and write to file
            # Update i to the next word after the break point
            line = ' '.join(arrayInput[i:breakPoints[i] + 1])
            print(line)
            out_file.write(line + '\n')
            i = breakPoints[i] + 1

    # return out_file

if __name__ == "__main__":
    # handles command line input
    if len(sys.argv) != 3:
        print("Incorrect format. Format answer as 'python format.py <L> <input_file>'")
    else:
        L = int(sys.argv[1])
        input_file = sys.argv[2]
        # calls algorithm on files
        fun(input_file, L)

# testing the function on "this is a sentence"
print(fun('input.txt', 10))