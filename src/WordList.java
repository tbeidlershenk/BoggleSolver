import java.lang.StringBuilder;

public class WordList extends LinkedList<String> {
    
    // first scanning of WordList
    // removes words containing characters not in grid
    public boolean removeImpossibleWords (char [] letters) {
        
        char[] sorted = Boggle.sortLetters(letters);
        int initialsize = numberOfEntries;
        Node curr = this.firstNode;
        int count = 0;
        int pos = 1;

        while (count < initialsize) {
            
            char[] copy = sorted.clone();
            boolean remove = false;

            for (char a: curr.data.toCharArray()) {
                for (int k = 0; k < copy.length; k++) {
                    if (a == copy[k]) {
                        copy[k] = '0';
                        break;
                    } else if ((copy[k] != '0' && copy[k] > a) || k == copy.length-1) {
                        remove = true;
                        break;
                    }
                }
                if (remove) {
                    remove(pos);
                    break;
                }
            }
            if (!remove) {
                pos++;
            }
            curr = curr.next;
            count++;

        }
        return true;
    }

    // second scanning of WordList
    // runs a recursive search for each remaining word in the list 
    // after removeImpossibleWords() has been called
    public void findWords (char [][] grid) {
        
        Node curr = firstNode;
        int pos = 0;
        int count = 0;
        int rows = grid[0].length;
        int cols = grid.length;
        int initialSize = numberOfEntries;

        // Loop through remaining words
        while (count < initialSize) {
            boolean found = false;

            //System.out.println("Searching For: " + curr.data.toString());
            // Loop through rows
            for (int r = 0; r < rows && !found; r++) {
                // Loop through columns
                for (int c = 0; c < cols && !found; c++) {
                    
                    // If word is found, leave in list
                    if (recursiveFind(r, c, curr.data.toString(), 0, grid)){
                        pos++;
                        //System.out.println(curr.data.toString() + " FOUND");
                        found = true;
                        
                        //System.out.println("Word Found: " + curr.data.toString());
                    }
                    //System.out.println("Row: " + r);
                    //System.out.println("Column: " + c);

                }
            }  
            curr = curr.next;
            count++;
            
            if (!found) {
                //System.out.println(numberOfEntries);
                //System.out.println(pos);

                remove(pos+1);
                //System.out.println(numberOfEntries);
                //System.out.println("Word Not Found: " + curr.data.toString());
                //System.out.println("Word at pos: " + getNodeAt(pos).data.toString());   
            }
        }
    }
    
    // recursive method to search for a word in the grid
    // Returns true if word is found, false otherwise
    public boolean recursiveFind (int r, int c, String word, int loc, char [][] grid) {

        //System.out.println("findWord: " + r + ":" + c + " " + word + ": " + loc); // trace code
		StringBuilder currWord = new StringBuilder("");
		// Check boundary conditions
		if (r >= grid.length || r < 0 || c >= grid[0].length || c < 0)
			return false;
		else if (grid[r][c] != word.charAt(loc))  // char does not match (offset for capital/lowercase)
			return false;
		else {
            // current character matches
			grid[r][c] = Character.toUpperCase(grid[r][c]);  // Change it to
				// upper case.  This serves two purposes:
				// 1) It will no longer match a lower case char, so it will
				//    prevent the same letter from being used twice
				// 2) It will show the word on the board when displayed
			currWord.append(grid[r][c]);
            /*for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    System.out.print(grid[i][j] + " ");
                }
                System.out.println();
            }*/
			//System.out.println("Curr: " + currWord);
			boolean answer;
			if (loc == word.length()-1)		// base case - word found and we
				answer = true;				// are done!
				
			else	// Still have more letters to match, so recurse.
			{		// Try all four directions if necessary.
				answer = recursiveFind(r, c+1, word, loc+1, grid);  // Right
				if (!answer)
					answer = recursiveFind(r+1, c, word, loc+1, grid);  // Down
				if (!answer)
					answer = recursiveFind(r, c-1, word, loc+1, grid);  // Left
				if (!answer)
					answer = recursiveFind(r-1, c, word, loc+1, grid);  // Up

                // check diagonals
                if (!answer)
					answer = recursiveFind(r+1, c+1, word, loc+1, grid);  // Down
				if (!answer)
					answer = recursiveFind(r+1, c-1, word, loc+1, grid);  // Left
				if (!answer)
					answer = recursiveFind(r-1, c+1, word, loc+1, grid);  // Up
                if (!answer)
					answer = recursiveFind(r-1, c-1, word, loc+1, grid);  // Up

				// If answer was not found, backtrack.  Note that in order to
				// backtrack for this algorithm, we need to move back in the
				// board (r and c) and in the word index (loc) -- these are both 
				// handled via the activation records, since after the current AR 
				// is popped, we revert to the previous values of these variables.
				// However, we also need to explicitly change the character back
				// to lower case before backtracking.
				if (!answer)
				{
					grid[r][c] = Character.toLowerCase(grid[r][c]);
					currWord.deleteCharAt(currWord.length()-1);
					//System.out.println("Curr: " + currWord);
				}
			}
            grid[r][c] = Character.toLowerCase(grid[r][c]);  // Change it to

			//System.out.println("findWord: " + r + ":" + c + " " + word + ": " + loc + " FINISHED"); // trace code
			return answer;
		}
    }
    
    
    
    public void printList() {
        Node curr = firstNode;
        for (int i = 0; i < numberOfEntries; i++) {
            System.out.println(curr.data.toString());
            curr = curr.next;
        }
    }

    public String[] toArray() {
        Node curr = firstNode;
        String[] arr = new String[numberOfEntries];
        for (int i = 0; i < numberOfEntries; i++) {
            arr[i] = curr.data.toString();
            curr = curr.next;
        }
        return arr;
    }

    public String toString() {
        Node curr = firstNode;
        String str = "";
        for (int i = 0; i < numberOfEntries; i++) {
            str += curr.data.toString() + " ";
            curr = curr.next;
        }
        return str;
    }

}
