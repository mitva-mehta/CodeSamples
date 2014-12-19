
This folder contains some code from my project ‘Mini Search Engine’.

Brief description of the project: Given a large corpus of web documents, the application finds the 
documents that are most relevant to a given query. It consists of a tokenizer, an indexer, and a ranker 
that uses scoring functions to rank documents based on their relevance to a query.

There are 3 java files that I have included:

1. Tokenizer.java - This class is used to tokenize the contents of each web document, and apply
		    stopping and stemming to the words in the documents. After applying stopping
		    and stemming, it creates a forward index which is written to doc_index.txt


2. IndexInversion.java - This class inverts the forward index created by Tokenzier.java and stores
			 it as an inverted index (which is based on terms rather than documents)
			 into term_index.txt. It also calculates a byte offset for the location
			 of each term in term_index.txt and saves this information into term_info.txt
			 so that looking up a term in the index becomes fast as we can directly jump
			 to the byte offset in the index file.


3. ReadIndex.java - This code is used to look up the index from the command line. Given a term or a 
		    document name, or both, we will get corresponding information by reading the index.
		    This returns information like term frequency in corpus, number of terms in a document,
		    positions of a term in a document, etc.



NOTE: 	Apart from these files, there are a few more classes that I used in the project for implementing scoring
	functions, and ranking documents for a given query. I have not included those here, but I can provide
	those files too if needed. 
	
	Also let me know if you would like to look at the output files e.g. term_index.txt. I haven’t included
	output files because of their huge size.