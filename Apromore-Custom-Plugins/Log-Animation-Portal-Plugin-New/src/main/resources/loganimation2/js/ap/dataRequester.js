
class DataRequester {
    /**
     * @param {Buffer} buffer
     * @param {String} _receiveData
     * @param {Boolean} _hasDataRequestError
     * @param {String} _pluginExecutionId
     */
    constructor(buffer) {
        this._buffer = buffer;
        this._receivedData = undefined;
        this._hasDataRequestError = false;

        this._workerProxy = new Worker("/loganimation2/js/ap/dataRequestWorker.js");
        let self = this;
        this._workerProxy.onmessage = function(e) {
            let result = e.data;
            if (result.success) {
                self._receivedData = result;
                if (self._buffer) {
                    self._buffer.writeNextChunk(result);
                }
            }

            //Testing
            console.log("Result: ");
            console.log("Response code: " + result.code);
            console.log("Response text: " + result.data);

            //this.doPointlessComputationsWithBlocking();
        }
    }

    /**
     *
     * @param {Number} frameIndex
     * @param {String} pluginExecutionId
     * @param {Buffer} buffer
     */
    requestData(frameIndex, pluginExecutionId) {
        this._workerProxy.postMessage({startFrame: frameIndex, pluginExecutionId: pluginExecutionId});
    }

    getReceivedData() {
        return this._receivedData;
    }

    hasDataRequestError() {
        return this._hasDataRequestError;
    }

    calculatePrimes(iterations, multiplier) {
        var primes = [];
        for (var i = 0; i < iterations; i++) {
            var candidate = i * (multiplier * Math.random());
            var isPrime = true;
            for (var c = 2; c <= Math.sqrt(candidate); ++c) {
                if (candidate % c === 0) {
                    // not prime
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                primes.push(candidate);
            }
        }
        return primes;
    }

    doPointlessComputationsWithBlocking() {
        var primes = calculatePrimes(iterations, multiplier);
        console.log(primes);
    }
}
