function openModal() {
    document.getElementById("editInfoModal").style.display = "block";
}

function closeModal() {
    document.getElementById("editInfoModal").style.display = "none";
}

window.onclick = function(event) {
    if (event.target == document.getElementById("editInfoModal")) {
        closeModal();

    }
}

