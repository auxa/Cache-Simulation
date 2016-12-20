
public class PLRUCacheSet {
	public int[] pseudoCache;
	public int[] tags;
	public int numberOfTagsPerSet;
	PLRUCacheSet(int numberOfTagsPerSet){
		this.pseudoCache = new int[numberOfTagsPerSet*2];
		this.tags = new int[numberOfTagsPerSet];
		this.numberOfTagsPerSet = numberOfTagsPerSet;
	
		for(int i =0; i< numberOfTagsPerSet; i++){
			tags[i]=-1;
		}
	}
}