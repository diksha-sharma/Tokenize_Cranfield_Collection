import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;


public class Tokenizer
{

	ArrayList<Token> alVocab = new ArrayList<Token>();
	ArrayList<Document> alDocument = new ArrayList<Document>();
	int iUniqueTokenCount = 0;
	int iTotalTokenCount = 0;
	long lStartTime = 0;
	long lEndTime = 0;
	
	//Program execution starts here
	public static void main(String args[])
	{
	
		//**************************
		// Start program execution
		//**************************
		String sDirectory = args[0];
		Tokenizer ctTokenizer = new Tokenizer();
		ctTokenizer.lStartTime = System.currentTimeMillis();
		ctTokenizer.readFilesAndProcessTokens(sDirectory);
		ctTokenizer.lEndTime = System.currentTimeMillis();
		
		System.out.println();
		System.out.println("Time taken to tokenize:  " + ((ctTokenizer.lEndTime - ctTokenizer.lStartTime)/1000) + " seconds");
		System.out.println();
		
		Porter pStemmer = new Porter();
		pStemmer.startExecution(args[0]);
		
	}//End of method main
	
	
	//This method reads a file from data dump one at a time and extracts all tokens - removing all stop words
	public void readFilesAndProcessTokens(String sTempDirectory)
	{
		
		int iFileCount = new File(sTempDirectory).listFiles().length;
		String fFileList[] = new File(sTempDirectory).list();
		File fFile;
		
		FileInputStream fisInputFile;
		DataInputStream disInput;
		BufferedReader brInputReader;
		
		for(int iFileIndex = 0; iFileIndex < iFileCount; iFileIndex++)
		{
				
			try
			{
				
				//*****************************************
				// Start processing a file from collection
				//*****************************************
				Document alTempDocument = new Document();
				fFile = new File(sTempDirectory + fFileList[iFileIndex]);
				alTempDocument.sDocumentName = fFileList[iFileIndex];
				//fFile = new File(sTempDirectory + fFileList[0]);
				//alTempDocument.sDocumentName = fFileList[0];
				
				fisInputFile = new FileInputStream(fFile);
				disInput = new DataInputStream(fisInputFile);
				brInputReader = new BufferedReader(new InputStreamReader(disInput));
				
				StringTokenizer stStringTokenizer;
				String sInputLine = null;
				String sWord = null;
				boolean bUnique = true;
				int iDocCount = 0;
				boolean bLower = false;

				String sSpecialCharRegEx = "[$&+,:;=?@#|'<>_~`.-^*()%!]";
				Pattern pSpecialCharPattern = Pattern.compile(sSpecialCharRegEx);
				Matcher mSpecialCharMatcher;
				boolean bSpecialCharMatch = false; 
				
				String sNumberRegEx = "[0-9]";
				Pattern pNumberPattern = Pattern.compile(sNumberRegEx);
				Matcher mNumberMatcher;
				boolean bNumberMatch = false;
				
				//String sCapsRegEx = "([A-Z]+)+";
				//Pattern pCapsPattern = Pattern.compile(sCapsRegEx);
				//Matcher mCapsMatcher;
				boolean bCapsMatch = false;
				
				//*************************************
				// Read till the last line of the file
				//*************************************
				while((sInputLine = brInputReader.readLine()) != null)
				{
					
					//Read next line
					stStringTokenizer = new StringTokenizer(sInputLine);
					
					//For each new word encountered check if the word is a number, a stop word, <tag>, </tag>
					while(stStringTokenizer.hasMoreTokens())
					{
						
						bUnique = true;
						sWord = stStringTokenizer.nextToken();
						
						//*********************************
						// Removing any spaces from words
						//*********************************
						sWord = sWord.trim();
						
						//*********************************************
						// Handling tokens with numbers - to be ignored
						//*********************************************
						mNumberMatcher = pNumberPattern.matcher(sWord);
						bNumberMatch = mNumberMatcher.find();
						
						if(bNumberMatch == false )
						{
						
							//*************************************************************************
							// Handling tokens that begin with < and end with > (tags)- to be ignored
							//*************************************************************************
							if(((sWord.contains("<")) || (sWord.contains(">"))))
							{
								
								//Do nothing if the word has <> - it is tag and can be ignored
								
								
							}//End of if(((sWord.contains("<")) || (sWord.contains(">"))))
							else if(!((sWord.contains("<")) || (sWord.contains(">"))))
							{
								
								//*************************************************************************
								// If the word is not a tag -Checking for Possessives - remove 's from end
								//*************************************************************************
								if(sWord.endsWith("'s"))
								{
									//Remove 's from the end
									sWord = sWord.substring(0, sWord.length()-2);
									
								
								}
								//else if(!sWord.endsWith("'s"))
								//{
									
								//**********************************************************************
								// If the word does not contain possessive 's but contains ' - remove '
								//**********************************************************************
								//if(sWord.contains("'"))
								//{
									
									//sWord = sWord.replace("'", "");
									
								//}//End of if(sWord.contains("'"))
								//else if(!sWord.contains("'"))
								//{
									
								//************************************************************
								// If the word contains any special characters - remove them
								//************************************************************
								//mSpecialCharMatcher = pSpecialCharPattern.matcher(sWord);
								//bSpecialCharMatch = mSpecialCharMatcher.find();
								
								//System.out.println("Over here:   " + sWord);
								
								//if(bSpecialCharMatch == true)
								//{
									
								sWord = sWord.replace("!", "");
								sWord = sWord.replace("@", "");
								sWord = sWord.replace("#", "");
								sWord = sWord.replace("?", "");
								sWord = sWord.replace("'", "");
								sWord = sWord.replace(":", "");
								sWord = sWord.replace(";", "");
								sWord = sWord.replace("=", "");
								sWord = sWord.replace("+", "");
								sWord = sWord.replace("$", "");
								sWord = sWord.replace("%", "");
								sWord = sWord.replace("^", "");
								sWord = sWord.replace("&", "");
								sWord = sWord.replace("*", "");
								sWord = sWord.replace("(", "");
								sWord = sWord.replace(")", "");
								sWord = sWord.replace("|", "");
								sWord = sWord.replace("-", "");
								sWord = sWord.replace("/", "");
								sWord = sWord.replace("\\", "");
								sWord = sWord.replace(".", "");
								sWord = sWord.replace(",", "");
								sWord = sWord.replace("~", "");
								sWord = sWord.replace("`", "");
								sWord = sWord.replace("_", "");
								sWord = sWord.replace("[", "");
								sWord = sWord.replace("]", "");
								sWord = sWord.replace("{", "");
								sWord = sWord.replace("}", "");
									

								//}//End of if(bSpecialCharMatch == true)
									
								//System.out.println(sWord);
								
								//System.out.println("Over here:   " + sWord);
								
								//*****************************************
								// If the word is empty string - ignore it
								//*****************************************
								if(!sWord.isEmpty())
								{
									
									//***************************************************************************
									// If the word is single character but not the stop word "a" then ignore it
									//***************************************************************************
									if(sWord.length() > 1 || sWord.contentEquals("a"))
									{
					
										//mCapsMatcher = pCapsPattern.matcher(sWord);
										//bCapsMatch = mCapsMatcher.find();
										
										bLower = false;
										for(int i=0; i < (sWord.length()); i++)
										{
											
											if(Character.isLowerCase(sWord.charAt(i)))
											{
												
												bLower = true;
												
											}
											
										}
										
										//System.out.println("Before:   " + sWord);
										if(bLower == true)
										{
										
											sWord = sWord.toLowerCase();
											
										}
										
										//System.out.println("After:   " + sWord);
										
										//******************************
										// Add the word to tokens list
										//******************************
										Token alTempToken = new Token();
										alTempToken.sToken = sWord;
										//alTempToken.iLengthDocList = alTempToken.iLengthDocList + 1;
										alTempToken.iFrequencyInCorpus = alTempToken.iFrequencyInCorpus + 1;
										
										for(int iTokenIndex = 0; iTokenIndex < iUniqueTokenCount; iTokenIndex++)
										{
											
											if(alVocab.get(iTokenIndex).sToken.equals(sWord))
											{
												
												bUnique = false;
												alVocab.get(iTokenIndex).iFrequencyInCorpus = alVocab.get(iTokenIndex).iFrequencyInCorpus + 1;
												//alVocab.get(iTokenIndex).iLengthDocList = alVocab.get(iTokenIndex).iLengthDocList + 1;
												
											}//End of if(alVocab.get(iTokenIndex).sToken.equals(sWord))
											
										}//End of for(int iTokenIndex = 0; iTokenIndex < iUniqueTokenCount; iTokenIndex++)
										
										if(bUnique == true)
										{
											
											alVocab.add(alTempToken);
											//System.out.println(sWord);
											iUniqueTokenCount = iUniqueTokenCount + 1;
											
										}//End of if(bUnique == true)
										
									
									}//End of if(sWord.length() > 1 || sWord.contentEquals("a"))
									
									
								}//End of if(!sWord.isEmpty())
									
									
								//}//End of else if(!sWord.contains("'"))
									
									
								//}//else if(!sWord.contains("'s"))
								
								
							}//End of else if(!((sWord.contains("<")) || (sWord.contains(">"))))							

							
						}//End of if(bNumberMatch == false)
						else if(bNumberMatch == true)
						{

							//*******************************************************************************************
							// If the word contains only upper case letters with/without numbers but starting with 
							// at least one upper case letter then don't convert to lower case as it might be an acronym
							// or tokens like drug name, flight  number etc. 
							//*******************************************************************************************
							//mCapsMatcher = pCapsPattern.matcher(sWord);
							//bCapsMatch = mCapsMatcher.find();
							
							bLower = false;
							for(int i=0; i < (sWord.length()-1); i++)
							{
								
								if(Character.isLowerCase(sWord.charAt(i)))
								{
									
									bLower = true;
									
								}
								
							}
							
							bCapsMatch = true;
							
							if(bLower == true)
							{
							
								bCapsMatch = false;
								
							}
							
							sWord = sWord.replace("1", "");
							sWord = sWord.replace("2", "");
							sWord = sWord.replace("3", "");
							sWord = sWord.replace("4", "");
							sWord = sWord.replace("5", "");
							sWord = sWord.replace("6", "");
							sWord = sWord.replace("7", "");
							sWord = sWord.replace("8", "");
							sWord = sWord.replace("9", "");
							sWord = sWord.replace("0", "");
							
							//System.out.println(sWord);

							if(bCapsMatch == true)
							{
								
								//*************************************************************************
								// Handling tokens that begin with < and end with > (tags)- to be ignored
								//*************************************************************************
								if(((sWord.contains("<")) || (sWord.contains(">"))))
								{
									
									//Do nothing if the word has <> - it is tag and can be ignored
									
									
								}//End of if(((sWord.contains("<")) || (sWord.contains(">"))))
								else if(!((sWord.contains("<")) || (sWord.contains(">"))))
								{
									
									//*************************************************************************
									// If the word is not a tag -Checking for Possessives - remove 's from end
									//*************************************************************************
									if(sWord.endsWith("'S"))
									{
										//Remove 's from the end
										sWord = sWord.replace("'","");
										
									
									}
									else if(!sWord.endsWith("'S"))
									{
											
										//************************************************************
										// If the word contains any special characters - remove them
										//************************************************************
										//mSpecialCharMatcher = pSpecialCharPattern.matcher(sWord);
										//bSpecialCharMatch = mSpecialCharMatcher.find();
										
										//if(bSpecialCharMatch == true)
										//{
											
										//System.out.println(sWord);
										sWord = sWord.replace("!", "");
										sWord = sWord.replace("'", "");
										sWord = sWord.replace("@", "");
										sWord = sWord.replace("#", "");
										sWord = sWord.replace("?", "");
										sWord = sWord.replace(":", "");
										sWord = sWord.replace(";", "");
										sWord = sWord.replace("=", "");
										sWord = sWord.replace("+", "");
										sWord = sWord.replace("$", "");
										sWord = sWord.replace("%", "");
										sWord = sWord.replace("^", "");
										sWord = sWord.replace("&", "");
										sWord = sWord.replace("*", "");
										sWord = sWord.replace("(", "");
										sWord = sWord.replace(")", "");
										sWord = sWord.replace("|", "");
										sWord = sWord.replace("-", "");
										sWord = sWord.replace("/", "");
										sWord = sWord.replace("\\", "");
										sWord = sWord.replace(".", "");
										sWord = sWord.replace(",", "");
										sWord = sWord.replace("~", "");
										sWord = sWord.replace("`", "");
										sWord = sWord.replace("_", "");
										sWord = sWord.replace("[", "");
										sWord = sWord.replace("]", "");
										sWord = sWord.replace("{", "");
										sWord = sWord.replace("}", "");
											
										//}//End of if(bSpecialCharMatch == true)
										
										//*****************************************
										// If the word is empty string - ignore it
										//*****************************************
										if(!sWord.isEmpty())
										{
											
											//**************************************************************
											// If the word is "A" then it is most likely "a" so we keep it
											//**************************************************************											
											if(sWord.contentEquals("A"))
											{
												
												sWord =sWord.toLowerCase();
												
											}
																						
											//*************************************************
											// If the word is single character then ignore it
											//*************************************************
											if(sWord.length() > 1)
											{
																								
												//******************************
												// Add the word to tokens list
												//******************************
												Token alTempToken = new Token();
												alTempToken.sToken = sWord;
												//alTempToken.iLengthDocList = alTempToken.iLengthDocList + 1;
												alTempToken.iFrequencyInCorpus = alTempToken.iFrequencyInCorpus + 1;
												
												for(int iTokenIndex = 0; iTokenIndex < iUniqueTokenCount; iTokenIndex++)
												{
													
													if(alVocab.get(iTokenIndex).sToken.equals(sWord))
													{
														
														bUnique = false;
														alVocab.get(iTokenIndex).iFrequencyInCorpus = alVocab.get(iTokenIndex).iFrequencyInCorpus + 1;
														//alVocab.get(iTokenIndex).iLengthDocList = alVocab.get(iTokenIndex).iLengthDocList + 1;
														
													}//End of if(alVocab.get(iTokenIndex).sToken.equals(sWord))
													
												}//End of for(int iTokenIndex = 0; iTokenIndex < iUniqueTokenCount; iTokenIndex++)
												
												//System.out.println(sWord);
												
												if(bUnique == true)
												{
													
													alVocab.add(alTempToken);
													//System.out.println("Added: " + sWord);
													iUniqueTokenCount = iUniqueTokenCount + 1;
													
												}//End of if(bUnique == true)
												
											
											}//End of if(sWord.length() > 1 || sWord.contentEquals("a"))
											
											
										}//End of if(!sWord.isEmpty())
																					
										
									}//else if(!sWord.contains("'S"))
									
									
								}//End of else if(!((sWord.contains("<")) || (sWord.contains(">"))))							

								
							}//End of if(bCapsMatch == true)
							else if(bCapsMatch == false)
							{
								
								sWord = sWord.toLowerCase();
								
								//*************************************************************************
								// Handling tokens that begin with < and end with > (tags)- to be ignored
								//*************************************************************************
								if(((sWord.contains("<")) || (sWord.contains(">"))))
								{
									
									//Do nothing if the word has <> - it is tag and can be ignored
									
									
								}//End of if(((sWord.contains("<")) || (sWord.contains(">"))))
								else if(!((sWord.contains("<")) || (sWord.contains(">"))))
								{
									
									//*************************************************************************
									// If the word is not a tag -Checking for Possessives - remove 's from end
									//*************************************************************************
									if(sWord.endsWith("'s"))
									{
										//Remove 's from the end
										sWord = sWord.substring(0, sWord.length()-2);
										
									
									}									

									sWord = sWord.replace("'", "");
									sWord = sWord.replace("!", "");
									sWord = sWord.replace("@", "");
									sWord = sWord.replace("#", "");
									sWord = sWord.replace("?", "");
									sWord = sWord.replace(":", "");
									sWord = sWord.replace(";", "");
									sWord = sWord.replace("=", "");
									sWord = sWord.replace("+", "");
									sWord = sWord.replace("$", "");
									sWord = sWord.replace("%", "");
									sWord = sWord.replace("^", "");
									sWord = sWord.replace("&", "");
									sWord = sWord.replace("*", "");
									sWord = sWord.replace("(", "");
									sWord = sWord.replace(")", "");
									sWord = sWord.replace("|", "");
									sWord = sWord.replace("-", "");
									sWord = sWord.replace("/", "");
									sWord = sWord.replace("\\", "");
									sWord = sWord.replace(".", "");
									sWord = sWord.replace(",", "");
									sWord = sWord.replace("~", "");
									sWord = sWord.replace("`", "");
									sWord = sWord.replace("_", "");
									sWord = sWord.replace("[", "");
									sWord = sWord.replace("]", "");
									sWord = sWord.replace("{", "");
									sWord = sWord.replace("}", "");												

									
									//*****************************************
									// If the word is empty string - ignore it
									//*****************************************
									if(!sWord.isEmpty())
									{
										
										//*************************************************
										// If the word is single character then ignore it
										//*************************************************
										if((sWord.length() > 1) || (sWord.contentEquals("a")))
										{
																													
											//******************************
											// Add the word to tokens list
											//******************************
											Token alTempToken = new Token();
											alTempToken.sToken = sWord;
											//alTempToken.iLengthDocList = alTempToken.iLengthDocList + 1;
											alTempToken.iFrequencyInCorpus = alTempToken.iFrequencyInCorpus + 1;
											
											for(int iTokenIndex = 0; iTokenIndex < iUniqueTokenCount; iTokenIndex++)
											{
												
												if(alVocab.get(iTokenIndex).sToken.equals(sWord))
												{
													
													bUnique = false;
													alVocab.get(iTokenIndex).iFrequencyInCorpus = alVocab.get(iTokenIndex).iFrequencyInCorpus + 1;
													//alVocab.get(iTokenIndex).iLengthDocList = alVocab.get(iTokenIndex).iLengthDocList + 1;
													
												}//End of if(alVocab.get(iTokenIndex).sToken.equals(sWord))
												
											}//End of for(int iTokenIndex = 0; iTokenIndex < iUniqueTokenCount; iTokenIndex++)
											
											//System.out.println(sWord);
											
											if(bUnique == true)
											{
												
												alVocab.add(alTempToken);
												//System.out.println("Added: " + sWord);
												iUniqueTokenCount = iUniqueTokenCount + 1;
												
											}//End of if(bUnique == true)
											
										
										}//End of if(sWord.length() > 1 || sWord.contentEquals("a"))
										
										
									}//End of if(!sWord.isEmpty())
															
								}//End of else if(!((sWord.contains("<")) || (sWord.contains(">"))))
								
							}//End of else if(bCapsMatch == false)
							
							
						}//End of else if(bNumberMatch == true)
						
						
					}//End of while(stStringTokenizer.hasMoreTokens())				
					
					
				}//End of while((sInputLine = brInputReader.readLine()) != null)
				
				
			}//End of try block
			catch(Exception e)
			{
				
				System.err.println("File not found exception in extractTokens method of Tokenizer class...");
				
			}//End of catch(Exception e)
			
			
		}//End of for(int iFileIndex = 0; iFileIndex < iFileCount; iFileIndex++)
		
		//*******************************************************************************************
		//Printing the total number of  tokens and the total number of unique tokens in the collection
		//*******************************************************************************************
		for(int iTokenFreqIndex = 0; iTokenFreqIndex < iUniqueTokenCount; iTokenFreqIndex++)
		{
			
			iTotalTokenCount = iTotalTokenCount + alVocab.get(iTokenFreqIndex).iFrequencyInCorpus;
			
			
		}//End of for(int iTokenFreqIndex = 0; iTokenFreqIndex < iUniqueTokenCount; iTokenFreqIndex++)
		
		System.out.println("Total number of tokens in the collection:  " + iTotalTokenCount);
		System.out.println("Total number of unique tokens in the collection:  " + iUniqueTokenCount);
		
		int iFreqEqualOne = 0;
		
		for(int iTokenFreqIndex = 0; iTokenFreqIndex < iUniqueTokenCount; iTokenFreqIndex++)
		{
			
			//System.out.println("Token:  " + alVocab.get(iTokenFreqIndex).sToken + "   Frequency:  " + alVocab.get(iTokenFreqIndex).iFrequencyInCorpus + "   # of Docs found:  " + alVocab.get(iTokenFreqIndex).iLengthDocList);
			
			if(alVocab.get(iTokenFreqIndex).iFrequencyInCorpus == 1)
			{
				
				iFreqEqualOne = iFreqEqualOne + 1;
				
			}//End of if(alVocab.get(iTokenFreqIndex).iFrequencyInCorpus == 1)
			
			
		}//End of for(int iTokenFreqIndex = 0; iTokenFreqIndex < iUniqueTokenCount; iTokenFreqIndex++)
		
		System.out.println("Total number of tokens with frequency equal to 1 in the collection:  " + iFreqEqualOne);
		
		//*******************************************************************************************
		//Sorting the tokens based on frequency to retrieve the most frequent tokens and print them
		//*******************************************************************************************
	    Token tempToken = new Token();
	    for(int iIndex1 = 0; iIndex1 < iUniqueTokenCount; iIndex1++)
	    {
	    	 
	        for(int iIndex2 = 1; iIndex2 < (iUniqueTokenCount - iIndex1); iIndex2++)
	        {
	                
	            if(alVocab.get((iIndex2 - 1)).iFrequencyInCorpus > alVocab.get(iIndex2).iFrequencyInCorpus)
	            {
	
	            	Collections.swap(alVocab, (iIndex2 - 1), iIndex2);
	                     
	            }
	                
	        }
	         
	    }
		
		int iTIndexTop30 = iUniqueTokenCount-30;
				
		System.out.println();
		System.out.println("Most Frequent tokens and their frequencies in Cranfield collection: ");
		for(int iMFIndex = (iUniqueTokenCount - 1); iMFIndex > iTIndexTop30; iMFIndex--)
		{
			
			System.out.println("Token: " + alVocab.get(iMFIndex).sToken + "   Frequency: " + alVocab.get(iMFIndex).iFrequencyInCorpus);
			
		}//End of for(int iMFIndex = 0; iMFIndex < 30; iMFIndex++)
		
		//************************************************************************************
		// Printing the average number of tokens in each document of the collection
		// = Total words in all documents (sum) / Total number of documents in the collection
		//************************************************************************************
		System.out.println();
		System.out.println("Average number of tokens in a document in the collection:  " + Math.round(iTotalTokenCount/iFileCount));

		//for(int iIndex1 = 0; iIndex1 < iUniqueTokenCount; iIndex1++)
		//{
			
			//System.out.println(alVocab.get(iIndex1).sToken);
			
		//}
		
		
	}//End of readFilesAndProcessTokens
	
	
}//End of class Tokenizer