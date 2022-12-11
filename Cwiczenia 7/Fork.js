class Fork {
    constructor() {
        this.taken = false;
    }

    acquire(callback, test = 1) {
        setTimeout(() => {
            if (!this.taken) {
                this.taken = true;
                if (callback) {
                    callback();
                }
            } else {
                this.acquire(callback,test = test + 1);
            }}, 2 * test
        )
    }

    release() {
        this.taken = false;
    }
}

export default Fork;