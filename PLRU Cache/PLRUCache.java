public class PLRUCache {
	static int hits;
	static int entry;
	public static void main(String[] args) {
		cacheTest(4, 0, 8);
		cacheTest(4, 1, 4);
		cacheTest(4, 2, 2);
		cacheTest(4, 3, 1);
	}
	
	
	public static int cacheSetInSet(PLRUCacheSet cache, int tag){
		int result = -1;
		for(int i=0; ((i< cache.numberOfTagsPerSet ) && (result ==-1)); i++){
		
			result = (cache.tags[i] ==tag)? i : -1;
		}
		return result;
	}

	//Returns index of the LRU line
	public static int cacheSetFindLRU(PLRUCacheSet cache){
		int i = 1;
		while(i < cache.numberOfTagsPerSet){
			if(cache.pseudoCache[i-1]==0){
				i *=2;
			}
			else{
				i=i*2 + 1;
			}
		}
		return i - cache.numberOfTagsPerSet;
	}
	//Update The LRU table
	public static void cacheSetUpdateLRU(PLRUCacheSet cache, int tagIndex){
		int i = tagIndex + cache.numberOfTagsPerSet;
		int direction = tagIndex % 2;
		while(i > 0){
			if(cache.pseudoCache[i-1]==direction){
				if(cache.pseudoCache[i-1] ==1){
					cache.pseudoCache[i-1]=0;
				}else{
					cache.pseudoCache[i-1]=1;
				}
			}else{
					if(cache.pseudoCache[i-1] ==1){
						cache.pseudoCache[i-1]=1;
					}else{
						cache.pseudoCache[i-1]=0;
					} 
			}
			direction = i %2;
			i /= 2;
		}
	}
	
	
	
	public static void cacheSetInsert(PLRUCacheSet cache, int tagToBeInserted){
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
	
	
	public static PLRUCacheDetail intialiseCache(int numberOfBitsPerAddress, int numberOfCacheLineOffsetBits, int numberOfSetBits, int numberOfTagsPerSet){
		int addressMask=0;
		int cacheSize = ((int)Math.pow(2, numberOfCacheLineOffsetBits))*numberOfTagsPerSet * ((int)Math.pow(2, numberOfSetBits));

		int numSets = (int) Math.pow(2, numberOfSetBits);

		PLRUCacheSet[] caches = new PLRUCacheSet[numSets];
		for(int i=0; i< numSets; i++){
			caches[i] = new PLRUCacheSet(numberOfTagsPerSet);
			System.out.println("Set Size " + numberOfTagsPerSet);
		}
		System.out.println("Testing a " +numSets+"-way Cache \n");
		PLRUCacheDetail cacheDetail = new PLRUCacheDetail( numberOfBitsPerAddress, numberOfCacheLineOffsetBits,
				numberOfSetBits, cacheSize, addressMask, caches);

		int mask =1;
		for(int i =0; i< numberOfBitsPerAddress; i++){
			cacheDetail.addressMask = cacheDetail.addressMask | mask;
			mask = mask <<1;
		}
		return cacheDetail;	
	}
	
	
	public static void cacheAccess(PLRUCacheDetail cacheDetail, int adr){
		int setNumber = ((adr << (cacheDetail.numberOfBitsPerAddress - cacheDetail.numberOfCacheLineOffsetBits - cacheDetail.numberOfSetBits)) &
				cacheDetail.addressMask)>> (cacheDetail.numberOfBitsPerAddress - cacheDetail.numberOfSetBits);
		int tag = adr >> (cacheDetail.numberOfCacheLineOffsetBits + cacheDetail.numberOfSetBits);
			cacheSetInsert(cacheDetail.cache[setNumber], tag);
	}

	static void cacheTest(int cacheOffsetBit, int setBits, int tagsPerSet){
		int numberOfBitsPerAdr =16;
		PLRUCacheDetail test = intialiseCache(numberOfBitsPerAdr, cacheOffsetBit, setBits, tagsPerSet);
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
