import java.lang.Math;

public class LRUCache {
	static int hits;
	static int entry;
	public static void main(String[] args) {
		cacheTest(4, 0, 8);
		cacheTest(4, 1, 4);
		cacheTest(4, 2, 2);
		cacheTest(4, 3, 1);
	}
	public static int cacheSetInSet(Cache cache, int tag){
		int result =-1;
		for(int i=0; i< cache.tagsPerSet; i++){
			if(cache.tags[i] == tag){
				result =i;
				return result;
			}
		}
		return result;
	}

	//Returns index of the LRU line
	public static int cacheSetFindLRU(Cache cache){
		int j, i;
		for(i=0; i< cache.tagsPerSet; i++){
			boolean isOne =false;
			for(j=0; (j<cache.tagsPerSet && !isOne); j++){
				if(cache.cache[i][j] !=0){
					isOne =true;
				}

			}
			if(!isOne){
				return i;
			}
		}
		System.out.println("Error Line");
		return -1;
	}
	//Update The LRU table
	public static void cacheSetUpdateLRU(Cache cache, int tagIndex){
		int i;
		for(i=0; i< cache.tagsPerSet; i++){
			cache.cache[tagIndex][i]=1;
		}
		for(i=0; i<cache.tagsPerSet; i++){
			cache.cache[i][tagIndex]=0;
		}
	}
	//
	public static void cacheSetInsert(Cache cache, int tagToBeInserted){
		entry++;
		int tagIndex = cacheSetInSet(cache, tagToBeInserted);
		if(tagIndex != -1){
			cacheSetUpdateLRU(cache, tagIndex);
			System.out.println("Entry " + entry +" is a Hit" );
			hits++;
		}else{
			int LRUTag = cacheSetFindLRU(cache);
			cache.tags[LRUTag] = tagToBeInserted;
			cacheSetUpdateLRU(cache, LRUTag);
		}
	}
	public static CacheDetail intialiseCache(int numberOfBitsPerAddress, int numberOfCacheLineOffsetBits, int numberOfSetBits, int numberOfTagsPerSet){
		int addressMask=0;
		int cacheSize = ((int)Math.pow(2, numberOfCacheLineOffsetBits))*numberOfTagsPerSet * ((int)Math.pow(2, numberOfSetBits));

		int numSets = (int) Math.pow(2, numberOfSetBits);

		Cache[] caches = new Cache[numSets];
		for(int i=0; i< numSets; i++){
			caches[i] = new Cache(numberOfTagsPerSet);
		}
		System.out.println("Testing a " +numSets+"-way Cache \n");
		CacheDetail cacheDetail = new CacheDetail( numberOfBitsPerAddress, numberOfCacheLineOffsetBits,
				numberOfSetBits, cacheSize, addressMask, caches);

		int mask =1;
		for(int i =0; i< numberOfBitsPerAddress; i++){
			cacheDetail.addressMask = cacheDetail.addressMask | mask;
			mask = mask <<1;
		}
		return cacheDetail;	
	}
	public static void cacheAccess(CacheDetail cacheDetail, int adr){
		int setNumber = ((adr << (cacheDetail.numberOfBitsPerAddress - cacheDetail.numberOfCacheLineOffsetBits - cacheDetail.numberOfSetBits)) &
				cacheDetail.addressMask)>> (cacheDetail.numberOfBitsPerAddress-cacheDetail.numberOfSetBits);
		int tag = adr >> (cacheDetail.numberOfCacheLineOffsetBits + cacheDetail.numberOfSetBits);
			cacheSetInsert(cacheDetail.cache[setNumber], tag);
	}

	static void cacheTest(int cacheOffsetBit, int setBits, int tagsPerSet){
		int numberOfBitsPerAdr =16;
		CacheDetail test = intialiseCache(numberOfBitsPerAdr, cacheOffsetBit, setBits, tagsPerSet);
		hits=0;
		entry=0;
		int cacheSize = ((int) Math.pow(2, cacheOffsetBit)) * ((int) Math.pow(2, setBits)) * tagsPerSet;

		System.out.println("Testing Cache " + cacheSize);

		cacheAccess(test, 0x0000);
		cacheAccess(test, 0x0004);
		cacheAccess(test, 0x000c);
		cacheAccess(test, 0x2200);
		cacheAccess(test, 0x00d0);
		cacheAccess(test, 0x00e0);
		cacheAccess(test, 0x1130);
		cacheAccess(test, 0x0028);
		cacheAccess(test, 0x113c);
		cacheAccess(test, 0x2204);
		cacheAccess(test, 0x0010);
		cacheAccess(test, 0x0020);
		cacheAccess(test, 0x0004);
		cacheAccess(test, 0x0040);
		cacheAccess(test, 0x2208);
		cacheAccess(test, 0x0008);
		cacheAccess(test, 0x00a0);
		cacheAccess(test, 0x0004);
		cacheAccess(test, 0x1104);
		cacheAccess(test, 0x0028);
		cacheAccess(test, 0x000c);
		cacheAccess(test, 0x0084);
		cacheAccess(test, 0x000c);
		cacheAccess(test, 0x3390);
		cacheAccess(test, 0x00b0);
		cacheAccess(test, 0x1100);
		cacheAccess(test, 0x0028);
		cacheAccess(test, 0x0064);
		cacheAccess(test, 0x0070);
		cacheAccess(test, 0x00d0);
		cacheAccess(test, 0x0008);
		cacheAccess(test, 0x3394);
		System.out.println("Number of Hits " + hits + " \n \n ");
	}


}

