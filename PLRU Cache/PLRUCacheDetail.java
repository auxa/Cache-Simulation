
public class PLRUCacheDetail {
	int numberOfBitsPerAddress;
	int numberOfCacheLineOffsetBits;
	int numberOfSetBits;
	int numberOfTagsPerSet;
	int cacheSize;				//in bytes
	int addressMask;
	PLRUCacheSet[] cache;
	
	PLRUCacheDetail (int numBPA, int numCacheLineOffset, int numberOfSetBits
			,int cacheSize,int addressMask, PLRUCacheSet[] cache){

		this.numberOfBitsPerAddress=numBPA;
		this.numberOfCacheLineOffsetBits= numCacheLineOffset;
		this.numberOfSetBits = numberOfSetBits;
		this.cache=cache;
		
		this.cacheSize= cacheSize;				
		this.addressMask= addressMask;

	}

}
