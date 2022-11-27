
const printAsync = (s, cb) => {
    const delay = Math.floor((Math.random()*1000)+500);
    setTimeout(() => {
        console.log(s);
        if (cb) cb();
    }, delay);
}

const task1 = (cb) => {
    printAsync("1", () => {
        task2(cb);
    });
}

const task2 = (cb) => {
    printAsync("2", () => {
        task3(cb);
    });
}

const task3 = (cb) => {
    printAsync("3", cb);
}

// wywolanie sekwencji zadan
// task1(() => {
//     console.log('done!');
// });


/*
** Zadanie:
** Napisz funkcje loop(n), ktora powoduje wykonanie powyzszej
** sekwencji zadan n razy. Czyli: 1 2 3 1 2 3 1 2 3 ... done
**
*/

const loop = (n) => {
    if (n !== 0) {
        task1(() => loop(n - 1));
    } else {
        printAsync('done!', () => {})
    }
}

loop(4);