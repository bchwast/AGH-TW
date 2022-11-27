let counter = 0;

const printAsync = (s, cb) => {
    const delay = Math.floor((Math.random()*1000)+500);
    setTimeout(function() {
        console.log(s);
        if (cb) cb();
    }, delay);
}

// Napisz funkcje (bez korzytania z biblioteki async) wykonujaca
// rownolegle funkcje znajdujace sie w tablicy
// parallel_functions. Po zakonczeniu wszystkich funkcji
// uruchamia sie funkcja final_function. Wskazowka:  zastosowc
// licznik zliczajacy wywolania funkcji rownoleglych


const inparallel = (parallel_functions, final_function) => {
    parallel_functions.forEach((fn) => {
        fn(() => {
            counter++;
            if (counter === parallel_functions.length) {
                final_function();
            }
        })
    })

}

A = (cb) => printAsync("A", cb);
B = (cb) => printAsync("B", cb);
C = (cb) => printAsync("C", cb);
Done = (cb) => printAsync("Done", cb);

inparallel([A, B, C], Done)

// kolejnosc: A, B, C - dowolna, na koncu zawsze "Done"