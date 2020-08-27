class Sort implements Runnable {
    int[] numbers;
    int[] pointers;
    int[] completed;
    String sortType;
    int[] alreadySorted;

    public Sort(int[] numbers, int[] pointers, int[] completed, int[] alreadySorted, String sortType) {
        this.numbers = numbers;
        this. pointers = pointers;
        this.completed = completed;
        this.sortType = sortType;
        this.alreadySorted = alreadySorted;

    }

    public void mergeSort(int max, int min) {
        int len = max-min + 1;
        int aMax = (len-1)/2 + min;
        int bMin = aMax + 1;
        if (aMax - min > 0) {
            mergeSort(aMax, min);
        }
        if (max - bMin > 0) {
            mergeSort(max, bMin);
        }
        pointers[0] = min;
        pointers[1] = bMin;
        int numbersIndex = 0;
        int[] newArray = new int[len];
        while (pointers[0] <= aMax && pointers[1] <= max) {
            sleep(10);
            if (numbers[pointers[0]] <= numbers[pointers[1]]) {
                newArray[numbersIndex] = numbers[pointers[0]];
                pointers[0]++;
            }
            else if (numbers[pointers[0]] > numbers[pointers[1]]) {
                newArray[numbersIndex] = numbers[pointers[1]];
                pointers[1]++;
            }
            numbersIndex++;
        }
        while (pointers[0] <= aMax) {
            newArray[numbersIndex] = numbers[pointers[0]];
            pointers[0]++;
            numbersIndex++;
        }
        while (pointers[1] <= max) {
            newArray[numbersIndex] = numbers[pointers[1]];
            pointers[1]++;
            numbersIndex++;
        }
        for (int i = 0; i < len; i++) {
            numbers[min+i] = newArray[i];
        }
    }

    public void bubbleSort() {
        boolean inOrder = false;
        for (int i = 0; i < numbers.length; i++) {
            inOrder = true;
            for (int a = 0; a < numbers.length-1-i; a++) {
                sleep(10);
                pointers[0] = a;
                pointers[1] = a+1;
                if (numbers[a] > numbers[a+1]) {
                    inOrder=false;
                    int temp = numbers[a];
                    numbers[a] = numbers[a+1];
                    numbers[a+1] = temp;
                }
            }
            if (inOrder) {
                break;
            }
            alreadySorted[numbers.length - 1-i] = 1;
        }
    }

    public void selectionSort() {
        //find the minimum element and moves it to the front of the array
        for (int i = 0; i < numbers.length; i++) {
            int min = i;
            pointers[0] = i;
            for (int a = i+1; a < numbers.length; a++) {
                pointers[1] = a;
                sleep(10);
                if (numbers[min] > numbers[a]) {
                    min = a;
                    pointers[0] = min;
                }
            }
            int temp = numbers[i];
            numbers[i] = numbers[min];
            numbers[min] = temp;
            alreadySorted[i] = 1;
        }
    }

    public void quickSort(int min, int max) {
        if (min < max) {
            int pivot = numbers[max];
            pointers[0] = min;
            for (int i = min; i < max; i++) {

                pointers[1] = i;
                sleep(10);
                if (numbers[i] <= pivot) {

                    int temp = numbers[i];
                    numbers[i] = numbers[pointers[0]];
                    numbers[pointers[0]] = temp;
                    pointers[0]++;
                }
            }
            numbers[max] = numbers[pointers[0]];
            numbers[pointers[0]] = pivot;

            quickSort(min, pointers[0] - 1);
            quickSort(pointers[0] + 1, max);
        }
    }

    public void insertionSort(int min, int max) {
        for (int i = min+1; i < max+1; i++) {
            if (numbers[i] < numbers[i-1]) {
                pointers[0] = i;
                int j = i-1;
                int value = numbers[i];
                while (j > min-1 && value < numbers[j]) {
                    sleep(10);
                    pointers[1] = j;
                    numbers[j+1] = numbers[j];
                    j--;
                }
                numbers[j+1] = value;
            }
        }
    }

    public void timSort() {
        final int run = 32;
        int index = 31;
        for (int i = 31; i < numbers.length; i = i+ run) {
            insertionSort(i-(run-1),i);
            index = i;
        }
        insertionSort(index+1, numbers.length-1);



        //merge
        for (int size = run; size < numbers.length; size = 2 * size) {
            if (size == 128) {
                int test = 4;
            }
            for (int i = 0; i < numbers.length; i = i + (2*size)) {
                //find the two arrays, so min mid and max
                int min = i;
                int max = Math.min((min + 2*size)-1, numbers.length-1);

                int numbersIndex = 0;
                int len = max-min + 1;
                int mid = min + size;
                if (mid > numbers.length) {
                    break;
                }
                int[] newArray = new int[len];

                pointers[0] = min;
                pointers[1] = mid;

                while (pointers[0] < mid && pointers[1] <= max) {
                    sleep(10);
                    if (numbers[pointers[0]] <= numbers[pointers[1]]) {
                        newArray[numbersIndex] = numbers[pointers[0]];
                        pointers[0]++;
                    }
                    else if (numbers[pointers[0]] > numbers[pointers[1]]) {
                        newArray[numbersIndex] = numbers[pointers[1]];
                        pointers[1]++;
                    }
                    numbersIndex++;
                }
                while (pointers[0] < mid) {
                    if (numbersIndex >= newArray.length) {
                        int t = 4;
                    }
                    newArray[numbersIndex] = numbers[pointers[0]];
                    pointers[0]++;
                    numbersIndex++;
                }
                while (pointers[1] <= max) {
                    if (numbersIndex >= newArray.length) {
                        int t = 4;
                    }
                    newArray[numbersIndex] = numbers[pointers[1]];
                    pointers[1]++;
                    numbersIndex++;
                }
                for (int a = 0; a < len; a++) {
                    numbers[min+a] = newArray[a];
                }
            }
        }

    }

    @Override
    public void run() {
        switch (sortType) {
            case "Merge Sort" :
                mergeSort(numbers.length-1,0);
                break;
            case "Bubble Sort" :
                bubbleSort();
                break;
            case "Selection Sort":
                selectionSort();
                break;
            case "Quick Sort":
                quickSort(0,numbers.length-1);
                break;
            case "Insertion Sort":
                insertionSort(0,numbers.length-1);
                break;
            case "Tim Sort":
                timSort();
                break;
        }


        pointers[0] = -2;
        pointers[1] = -2; // -2 means the list is finished sorting
        checkSort();
    }


    public void checkSort() {
        for (int i = 0; i < numbers.length-1; i++) {
            sleep(10);
            if (numbers[i] <= numbers[i+1]) {
                completed[i] = 1;
            }
            else {
                //it failed
            }
            if (i==numbers.length-2) {
                completed[i+1] = 1;
            }
        }
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        }
        catch (Exception e) {}
    }
}