class Waiter {
    constructor(count) {
        this.count = count;
    }

    acquire(callback, test = 1) {
        let self = this;
        setTimeout(() => {
            if (self.count > 0) {
                self.count--;
                if (callback) {
                    callback();
                }
            } else {
                self.acquire(callback, test = test + 1)
            }
        }, 2 * test);
    }

    release() {
        this.count++;
    }
}

export default Waiter;