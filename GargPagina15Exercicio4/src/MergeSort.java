public class MergeSort {

	public static void main(String args[]) { 
		int arr[] = {12, 11, 13, 5, 6, 7}; 

		System.out.println("Given Array"); 
		printArray(arr); 

		try {
			ThreadSort threadSort = new ThreadSort(arr, 0, arr.length - 1);
			threadSort.start();
			threadSort.join();
		} catch (InterruptedException interruptedException) {
		}

		System.out.println("\nSorted array"); 
		printArray(arr); 
	}

	static void printArray(int arr[]) { 
		int n = arr.length; 
		for (int i = 0; i < n; ++i) 
			System.out.print(arr[i] + " "); 
		System.out.println(); 
	}
}