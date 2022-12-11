class Philosopher {
    constructor(id, forks, waiter, asym=false) {
        this.id = id;
        this.forks = forks;
        this.left = asym ? (id + 1) % forks.length : id % forks.length;
        this.right = asym ? id % forks.length : (id + 1) % forks.length;
        this.waiter = waiter;
    }

    start(count) {
        if (count > 0) {
            console.log('Philosopher ' + this.id + ' is hungry');
            this.forks[this.right].acquire(() => {
                console.log('Philosopher ' + this.id + ' acquired right fork');
                this.forks[this.left].acquire(() => {
                    console.log('Philosopher ' + this.id + ' acquired left fork and begins eating');
                    // setTimeout(() => {
                        console.log('Philosopher ' + this.id + ' finished eating');
                        this.forks[this.right].release();
                        this.forks[this.left].release();
                        console.log('Philosopher ' + this.id + ' begins thinking')
                        this.start(count - 1);
                    // }, Math.random() * 2000)
                });
            });
        }
    }

    startWithWaiter(count) {
        if (count > 0) {
            console.log('Philosopher ' + this.id + ' is hungry');
            this.waiter.acquire(() => {
                this.forks[this.right].acquire(() => {
                    console.log('Philosopher ' + this.id + ' acquired right fork');
                    this.forks[this.left].acquire(() => {
                        console.log('Philosopher ' + this.id + ' acquired left fork and begins eating');
                        // setTimeout(() => {
                            console.log('Philosopher ' + this.id + ' finished eating');
                            this.forks[this.right].release();
                            this.forks[this.left].release();
                            this.waiter.release();
                            console.log('Philosopher ' + this.id + ' begins thinking')
                            this.startWithWaiter(count - 1);
                        // }, Math.random() * 2000)
                    });
                });
            })
        }
    }
}

export default Philosopher;