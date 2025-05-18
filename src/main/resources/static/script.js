function analyzeResult(result) {
        if(result[0].probability > 0.8) {
            if(result[0].className === 'happy') {
                return 'This emoji is happy! 😊';
            }
            if(result[0].className === 'unhappy') {
                return 'This emoji is unhappy! 😞';
            }
        }
        return 'Unable to determine happiness.';
    }

    async function sendTestEmoji(url) {
        const response = await fetch(url);
        const blob = await response.blob();
        const file = new File([blob], url, { type: blob.type });
        
        const formData = new FormData();
        formData.append('image', file);
        showResult('Analyzing...', 'info');
        try {
            const response = await fetch('/analyze', {
                method: 'POST',
                body: formData
            });
            const result = await response.json();
            const emojiHappiness = analyzeResult(result);
            showResult(emojiHappiness, 'success');
        } catch (err) {
            showResult('Error analyzing emoji.', 'danger');
        }
    }

    function showResult(message, type) {
        const resultDiv = document.getElementById('result');
        resultDiv.innerHTML = message;
        resultDiv.className = `result-alert alert alert-${type} text-center fw-semibold`;
        resultDiv.classList.remove('d-none');
    }
    function showJDLResult(message, type) {
        const resultDiv = document.getElementById('jdlresult');
        resultDiv.innerHTML = message;
        resultDiv.className = `result-alert alert alert-${type} text-center fw-semibold`;
        resultDiv.classList.remove('d-none');
    }