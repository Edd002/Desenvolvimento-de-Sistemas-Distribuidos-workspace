public class ThreadSort extends Thread {

	private int arr[];
	int l;
	int r;

	public ThreadSort(int[] arr, int l, int r) {
		this.arr = arr;
		this.l = l;
		this.r = r;
	}

	@Override
	public void run() {
		sort(arr, l, r);
	}

	private void sort(int arr[], int l, int r) { 
		if (l < r) {
			// Find the middle point
			int m = (l + r) / 2;

			// Sort first and second halves
			Thread threadSortF = new ThreadSort(arr, l, m);
			threadSortF.start();
			try {
				threadSortF.join();
			} catch (InterruptedException interruptedException) { }

			Thread threadSortS = new ThreadSort(arr, m + 1, r); 
			threadSortS.start();
			try {
				threadSortS.join();
			} catch (InterruptedException interruptedException) { }

			// Merge the sorted halves
			merge(arr, l, m, r);
		}
	}

	/*
	private void sort(int arr[], int l, int r) { 
		if (l < r) {
			// Find the middle point
			int m = (l + r) / 2;

			// Sort first and second halves
			sort(arr, l, m);
			sort(arr , m + 1, r);

			// Merge the sorted halves
			merge(arr, l, m, r);
		}
	}
	 */

	private void merge(int arr[], int l, int m, int r) { 
		int n1 = m - l + 1;
		int n2 = r - m;

		int L[] = new int [n1];
		int R[] = new int [n2];

		for (int i=0; i<n1; ++i)
			L[i] = arr[l + i];
		for (int j=0; j<n2; ++j)
			R[j] = arr[m + 1+ j];

		int i = 0, j = 0;
		int k = l;
		while (i < n1 && j < n2) {
			if (L[i] <= R[j]) {
				arr[k] = L[i];
				i++;
			} else {
				arr[k] = R[j];
				j++;
			}
			k++;
		}

		while (i < n1) {
			arr[k] = L[i];
			i++;
			k++;
		}

		while (j < n2) {
			arr[k] = R[j];
			j++;
			k++;
		}
	}
}