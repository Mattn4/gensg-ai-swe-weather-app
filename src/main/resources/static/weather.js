const cityInputsDiv = document.getElementById('cityInputs');
const cityCountSelect = document.getElementById('cityCount');
function updateCityInputs() {
    const count = parseInt(cityCountSelect.value, 10);
    cityInputsDiv.innerHTML = '';
    for (let i = 1; i <= count; i++) {
        const label = document.createElement('label');
        label.textContent = `City ${i} Name:`;
        label.setAttribute('for', `city${i}`);
        const input = document.createElement('input');
        input.type = 'text';
        input.id = `city${i}`;
        input.name = `city${i}`;
        input.required = true;
        input.placeholder = "e.g. London";
        input.autocomplete = "off";
        cityInputsDiv.appendChild(label);
        cityInputsDiv.appendChild(input);
    }
}
cityCountSelect.addEventListener('change', updateCityInputs);
updateCityInputs();

document.getElementById('weatherForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    const count = parseInt(cityCountSelect.value, 10);
    let cities = [];
    let hasEmpty = false;
    for (let i = 1; i <= count; i++) {
        const val = document.getElementById(`city${i}`).value.trim();
        if (!val) {
            hasEmpty = true;
            break;
        }
        cities.push(val);
    }
    if (hasEmpty) {
        alert("Please enter valid city name(s).");
        return;
    }
    if (cities.length === 0) return;

    // Call backend API
    const resultsDiv = document.getElementById('results');
    resultsDiv.innerHTML = "Loading...";
    try {
        const response = await fetch('/api/weather', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({cities})
        });
        const data = await response.json();
        if (data.error) {
            resultsDiv.innerHTML = `<div class="error">${data.error}</div>`;
            return;
        }
        // Build table
        let html = `<div class="table-responsive"><table>
            <caption class="visually-hidden">Weather Results</caption>
            <tr><th scope="col">City</th><th scope="col">Temperature (°C)</th><th scope="col">Description</th></tr>`;
        for (const cityResult of data.results) {
            if (cityResult.error) {
                html += `<tr>
                    <td>${cityResult.city || 'N/A'}</td>
                    <td>N/A</td>
                    <td class="error">${cityResult.error}</td>
                </tr>`;
            } else {
                html += `<tr>
                    <td>${cityResult.city}</td>
                    <td>${cityResult.temperature_celsius}</td>
                    <td>${cityResult.description}</td>
                </tr>`;
            }
        }
        html += `</table></div>`;
        resultsDiv.innerHTML = html;
    } catch (err) {
        resultsDiv.innerHTML = `<div class="error">Failed to fetch weather data.</div>`;
    }
});