const printAsync = (s, cb) => {
    const delay = Math.floor((Math.random()*1000)+500);
    setTimeout(() => {
        console.log(s);
        if (cb) cb();
    }, delay);
}

printAsync("1", () => {
    printAsync("2", ()  => {
        printAsync("3", () => {
            console.log('done!');
        });
    });
});