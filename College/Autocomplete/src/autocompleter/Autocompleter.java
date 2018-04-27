package autocompleter;

import java.util.ArrayList;

public class Autocompleter implements AutocompleterInterface {

	// -----------------------------------------------------------
	// Fields
	// -----------------------------------------------------------
	TTNode root;


	// -----------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------
	Autocompleter () {
		root = null;
	}


	// -----------------------------------------------------------
	// Methods
	// -----------------------------------------------------------

	public boolean isEmpty () {
		return root == null;
	}

	public void addTerm (String toAdd) {
		addTerm(toAdd, root);
	}

	private void addTerm (String toAdd, TTNode tree) {

		// Base Case: String is empty or null
		if (toAdd.equals("") || toAdd == null) { 
			return; 
		}

		toAdd = normalizeTerm(toAdd);

		// Base Case: Empty Tree
		if (isEmpty()) {
			root = populateDown(toAdd);
			return;
		}

		int alphabetical = compareChars(toAdd.charAt(0), tree.letter);

		// if same letter
		if (alphabetical == 0) {
			if (tree.mid == null) {
				if (toAdd.length() > 1)
					tree.mid = populateDown(toAdd.substring(1));
			} else {
				addTerm(toAdd.substring(1), tree.mid);
			}
		} 
		// if lesser letter
		else if (alphabetical < 0) {
			if (tree.left == null) {
				tree.left = populateDown(toAdd);
			} else {
				//System.out.println(toAdd);
				addTerm(toAdd, tree.left);
			}
		} 
		// if greater letter
		else {
			if (tree.right == null) {
				tree.right = populateDown(toAdd);
			} else {
				//System.out.println(toAdd);
				addTerm(toAdd, tree.right);
			}
		}

	}

	public boolean hasTerm (String query) {
		return hasTerm(query, root);
	}

	private boolean hasTerm (String query, TTNode tree) {

		// Base Case True: String is empty => found word in tree!!
		if (query.equals("") || query == null) {
			return true;
		}

		// Base Case False: Current subtree is empty => no word in tree!!
		if (tree == null) {
			return false;
		}

		query = normalizeTerm(query);

		int alphabetical = compareChars(query.charAt(0), tree.letter);

		//Matching and Last letter:
		if (query.length() == 1 && alphabetical == 0) {
			return (tree.wordEnd && hasTerm(query.substring(1), tree.mid));
		}

		//Matching letter:
		if (alphabetical == 0) {
			return hasTerm(query.substring(1), tree.mid);
		}

		//Non matching letter:
		else if (alphabetical < 0) {
			return hasTerm(query, tree.left);
		} else {
			return hasTerm(query, tree.right);
		}
	}

	public String getSuggestedTerm (String query) {
		return getSuggestedTerm(query, root);
	}

	private String getSuggestedTerm (String query, TTNode tree) {

		query = normalizeTerm(query);

		String result = "";
		int stringIndex = 0;

		while (tree != null) {	
			// if still looking for letter in query
			if (stringIndex < query.length() ) {
				int alphabetical = compareChars(query.charAt(stringIndex), tree.letter);

				// same letter
				if (alphabetical == 0) {
					result += tree.letter;
					stringIndex++;
					if (stringIndex == query.length() && tree.wordEnd) {
						return result;
					}
					tree = tree.mid;
				}
				// lesser letter
				else if (alphabetical < 0) {
					if (tree.left == null) {
						return null;
					}
					tree = tree.left;
				} 
				// greater letter
				else {
					if (tree.right == null) {
						return null;
					}
					tree = tree.right;
				}

				// if found all letters in query
			} else {
				int alphabetical = compareChars(query.charAt(query.length() - 1), tree.letter);
				if (tree.wordEnd && alphabetical == 0) {
					return result + tree.letter;
				}
				result += tree.letter;
				tree = tree.mid;
			}
		} 

		return result;
	}

	public ArrayList<String> getSortedTerms () {
		return getSortedTerms("", root);
	}

	private ArrayList<String> getSortedTerms (String prefix, TTNode tree) {
		ArrayList<String> sortedList = new ArrayList<String>();

		if (tree != null) {
			if (tree.wordEnd) { 
				sortedList.add(prefix + tree.letter);
			}
			// recursion steps
			sortedList.addAll(getSortedTerms(prefix, tree.left));
			sortedList.addAll(getSortedTerms(prefix + tree.letter, tree.mid));
			sortedList.addAll(getSortedTerms(prefix, tree.right));
		}
		return sortedList;
	}


	// -----------------------------------------------------------
	// Helper Methods
	// -----------------------------------------------------------

	private String normalizeTerm (String s) {
		// Edge case handling: empty Strings illegal
		if (s == null || s.equals("")) {
			throw new IllegalArgumentException();
		}
		return s.trim().toLowerCase();
	}

	/*
	 * Returns:
	 *   int less than 0 if c1 is alphabetically less than c2
	 *   0 if c1 is equal to c2
	 *   int greater than 0 if c1 is alphabetically greater than c2
	 */
	private int compareChars (char c1, char c2) {
		return Character.toLowerCase(c1) - Character.toLowerCase(c2);
	}

	// [!] Add your own helper methods here!

	private TTNode populateDown(String suffix) {
		TTNode temproot = new TTNode(suffix.charAt(0), suffix.length() == 1);
		TTNode curr = temproot;
		for (int i = 1; i < suffix.length(); i++) {
			curr.mid = new TTNode (suffix.charAt(i), suffix.length() == i + 1);
			curr = curr.mid;
		}
		return temproot;
	}
	// -----------------------------------------------------------
	// TTNode Internal Storage
	// -----------------------------------------------------------

	/*
	 * Internal storage of autocompleter search terms
	 * as represented using a Ternary Tree with TTNodes
	 */
	private class TTNode {

		boolean wordEnd;
		char letter;
		TTNode left, mid, right;

		TTNode (char c, boolean w) {
			letter  = c;
			wordEnd = w;
			left    = null;
			mid     = null;
			right   = null;
		}

	}

}
