
public class Cache {
	
	
	
	public int[][] cache;
	public int[] tags;
	public int tagsPerSet;
	Cache(int numberOfTagsPerSet){
	
		this.tagsPerSet= numberOfTagsPerSet;
		this.cache = new int[tagsPerSet][tagsPerSet];
		this.tags = new int[tagsPerSet];
		for(int i =0; i< numberOfTagsPerSet; i++){
			tags[i]=-1;
		}
	}
}
