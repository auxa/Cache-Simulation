
public class CacheDetail {

	int numberOfBitsPerAddress;
	int numberOfCacheLineOffsetBits;
	int numberOfSetBits;
	int numberOfTagsPerSet;
	int cacheSize;				//in bytes
	int addressMask;
	Cache[] cache;
	CacheDetail (int numBPA, int numCacheLineOffset, int numberOfSetBits
			,int cacheSize,int addressMask, Cache[] cache){

		this.numberOfBitsPerAddress=numBPA;
		this.numberOfCacheLineOffsetBits= numCacheLineOffset;
		this.numberOfSetBits = numberOfSetBits;
		this.cache=cache;
		
		this.cacheSize= cacheSize;				
		this.addressMask= addressMask;

	}
}
