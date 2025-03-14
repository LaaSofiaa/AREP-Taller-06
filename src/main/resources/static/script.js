document.getElementById('propertyForm').addEventListener('submit', function (e) {
    e.preventDefault();
    createProperty();
});

function createProperty() {
    const property = {
        address: document.getElementById('newAddress').value,
        price: parseFloat(document.getElementById('newPrice').value),
        size: parseFloat(document.getElementById('newSize').value),
        description: document.getElementById('newDescription').value
    };

    fetch('https://gestionpropiedadesbackend.duckdns.org:8080/api/properties', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(property)
    })
    .then(response => response.json())
    .then(() => {
        showMessage('Propiedad agregada correctamente.', 'success');
        loadProperties();
        document.getElementById('propertyForm').reset();
    })
    .catch(() => showMessage('Error al agregar la propiedad.', 'error'));
}

function loadProperties() {
    fetch('https://gestionpropiedadesbackend.duckdns.org:8080/api/properties')
    .then(response => response.json())
    .then(properties => {
        const propertyTableBody = document.querySelector('#propertiesTable tbody');
        propertyTableBody.innerHTML = '';

        properties.forEach(property => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${property.id}</td>
                <td>${property.address}</td>
                <td>$${property.price}</td>
                <td>${property.size}m²</td>
                <td>${property.description}</td>
                <td>
                    <button onclick="deleteProperty(${property.id})">Eliminar</button>
                    <button onclick="editProperty(${property.id})">Editar</button>
                </td>
            `;
            propertyTableBody.appendChild(row);
        });
    })
    .catch(() => showMessage('Error al cargar las propiedades.', 'error'));
}

function deleteProperty(id) {
    fetch(`https://gestionpropiedadesbackend.duckdns.org:8080/api/properties/${id}`, {
        method: 'DELETE'
    })
    .then(() => {
        showMessage('Propiedad eliminada correctamente.', 'success');
        loadProperties();
    })
    .catch(() => showMessage('Error al eliminar la propiedad.', 'error'));
}

function editProperty(id) {
    fetch(`https://gestionpropiedadesbackend.duckdns.org:8080/api/properties/${id}`)
    .then(response => response.json())
    .then(property => {
        document.getElementById('newAddress').value = property.address;
        document.getElementById('newPrice').value = property.price;
        document.getElementById('newSize').value = property.size;
        document.getElementById('newDescription').value = property.description;

        const btn = document.querySelector('#propertyForm button');
        btn.textContent = "Actualizar Propiedad";
        btn.type = "button";
        btn.onclick = function () {
            updateProperty(id);
        };
    })
    .catch(() => showMessage('Error al cargar la propiedad.', 'error'));
}

function updateProperty(id) {
    const updatedProperty = {
        address: document.getElementById('newAddress').value,
        price: parseFloat(document.getElementById('newPrice').value),
        size: parseFloat(document.getElementById('newSize').value),
        description: document.getElementById('newDescription').value
    };

    fetch(`https://gestionpropiedadesbackend.duckdns.org:8080/api/properties/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedProperty)
    })
    .then(response => response.json())
    .then(() => {
        showMessage('Propiedad actualizada correctamente.', 'success');
        loadProperties();
        document.getElementById('propertyForm').reset();
        const btn = document.querySelector('#propertyForm button');
        btn.textContent = "Agregar Propiedad";
        btn.type = "submit";
        btn.onclick = null;
    })
    .catch(() => showMessage('Error al actualizar la propiedad.', 'error'));
}

function showMessage(message, type) {
    const messageDiv = document.getElementById('message');
    if (!messageDiv) return;

    messageDiv.textContent = message;
    messageDiv.className = `message ${type}`;
}

loadProperties();