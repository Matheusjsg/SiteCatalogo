let selectedFiles = [];

function previewFotos(event) {
    const files = Array.from(event.target.files);
    selectedFiles = files; // salva os arquivos escolhidos

    renderPreview();
}

function renderPreview() {
    const container = document.getElementById("preview-container");
    container.innerHTML = "";

    selectedFiles.forEach((file, index) => {
        const reader = new FileReader();
        reader.onload = function(e) {
            const div = document.createElement("div");
            div.style.position = "relative";
            div.style.display = "inline-block";

            // Imagem
            const img = document.createElement("img");
            img.src = e.target.result;
            img.style.width = "120px";
            img.style.height = "120px";
            img.style.objectFit = "cover";
            img.style.border = "1px solid #ccc";
            img.style.borderRadius = "8px";

            // Botão X
            const btn = document.createElement("span");
            btn.innerHTML = "✖";
            btn.style.position = "absolute";
            btn.style.top = "5px";
            btn.style.right = "5px";
            btn.style.cursor = "pointer";
            btn.style.background = "rgba(0,0,0,0.6)";
            btn.style.color = "white";
            btn.style.borderRadius = "50%";
            btn.style.padding = "2px 6px";
            btn.onclick = function() {
                removeFoto(index);
            };

            div.appendChild(img);
            div.appendChild(btn);
            container.appendChild(div);
        }
        reader.readAsDataURL(file);
    });

    // Atualiza o input para manter apenas as imagens selecionadas
    atualizarInput();
}

function removeFoto(index) {
    selectedFiles.splice(index, 1);
    renderPreview();
}

function atualizarInput() {
    const dataTransfer = new DataTransfer();
    selectedFiles.forEach(file => dataTransfer.items.add(file));
    document.getElementById("imagens").files = dataTransfer.files;
}