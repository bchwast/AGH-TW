import Fork from "./Fork.js";
import Philosopher from "./Philosopher.js";
import Waiter from "./Waiter.js";

const N = 5;
const forks = [];
const philosophers = [];
const waiter = new Waiter(N - 1);

for (let i = 0; i < N; i++) {
    forks.push(new Fork());
}

// Naive

// for (let i = 0; i < N; i++) {
//     philosophers.push(new Philosopher(i, forks, null));
// }
//
// for (let i = 0; i < N; i++) {
//     setTimeout(() => philosophers[i].start(10), Math.random() * 100);
//     // philosophers[i].start(10);
// }


// Asym

// for (let i = 0; i < N; i++) {
//     if (i % 2 === 1) {
//         philosophers.push(new Philosopher(i, forks, null, true));
//     } else {
//         philosophers.push(new Philosopher(i, forks, null));
//     }
// }
//
// for (let i = 0; i < N; i++) {
//     philosophers[i].start(10)
// }


// Waiter

for (let i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks, waiter));
}

for (let i = 0; i < N; i++) {
    philosophers[i].startWithWaiter(10)
}

