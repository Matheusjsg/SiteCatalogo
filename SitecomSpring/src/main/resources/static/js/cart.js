

    // Referências aos elementos do DOM
    const productsSection = document.querySelector('.grid-produtos');
    const cartList = document.getElementById('cart-list');
    const cartTotalSpan = document.getElementById('cart-total');
    const sendWhatsappBtn = document.getElementById('send-whatsapp-btn');

    // Array para armazenar os itens do carrinho
    let cart = [];

    /**
     * Salva o carrinho no localStorage.
     */
    function saveCart() {
        try {
            localStorage.setItem('shoppingCart', JSON.stringify(cart));
        } catch (e) {
            console.error("Não foi possível salvar o carrinho no localStorage.", e);
        }
    }

    /**
     * Carrega o carrinho do localStorage.
     */
    function loadCart() {
        try {
            const storedCart = localStorage.getItem('shoppingCart');
            if (storedCart) {
                cart = JSON.parse(storedCart);
            }
        } catch (e) {
            console.error("Não foi possível carregar o carrinho do localStorage.", e);
            cart = [];
        }
        renderCart();
    }

    /**
     * Renderiza a lista de itens do carrinho no HTML e atualiza o total.
     */
    function renderCart() {
        cartList.innerHTML = '';
        let total = 0;

        if (cart.length === 0) {
            cartList.innerHTML = '<li class="list-group-item text-center text-muted fst-italic">Seu carrinho está vazio.</li>';
            cartTotalSpan.textContent = 'R$ 0,00';
            sendWhatsappBtn.disabled = true;
            sendWhatsappBtn.classList.add('disabled');
            return;
        }
        
        sendWhatsappBtn.disabled = false;
        sendWhatsappBtn.classList.remove('disabled');

        cart.forEach(item => {
            const itemTotal = item.price * item.quantity;
            total += itemTotal;

            const li = document.createElement('li');
            li.className = 'list-group-item d-flex align-items-center justify-content-between';
            li.innerHTML = `
                <div class="d-flex align-items-center flex-grow-1">
                    <img src="${item.image}" alt="${item.name}" class="me-3 rounded" style="width:50px; height:50px; object-fit:cover;">
                    <div>
                        <div class="fw-bold">${item.name}</div>
                        <small class="text-muted">Quantidade: ${item.quantity}</small>
                    </div>
                </div>
                <div class="d-flex align-items-center">
                    <span class="fw-bold text-primary me-3">R$ ${(item.price * item.quantity).toFixed(2).replace('.', ',')}</span>
                    <button class="btn btn-sm btn-outline-danger remove-item-btn" data-id="${item.id}" aria-label="Remover ${item.name}">
                        <i class="fas fa-trash-alt"></i>
                    </button>
                </div>
            `;
            cartList.appendChild(li);
        });

        cartTotalSpan.textContent = `R$ ${total.toFixed(2).replace('.', ',')}`;
    }

    /**
     * Adiciona ou incrementa um item no carrinho.
     */
    function addToCart(item) {
        const existingItem = cart.find(cartItem => cartItem.id === item.id);
        if (existingItem) {
            existingItem.quantity++;
        } else {
            cart.push({ ...item, quantity: 1 });
        }
        saveCart();
        renderCart();
    }

    /**
     * Remove um item do carrinho.
     */
    function removeFromCart(itemId) {
        cart = cart.filter(item => item.id !== itemId);
        saveCart();
        renderCart();
    }

    /**
     * Gera a mensagem formatada para ser enviada via WhatsApp.
     */
    function generateWhatsAppMessage() {
        let message = "Olá! Gostaria de fazer o seguinte pedido:\n\n";
        cart.forEach(item => {
            message += `- ${item.name} (${item.quantity}x) - R$ ${(item.price * item.quantity).toFixed(2).replace('.', ',')}\n`;
        });
        const total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);
        message += `\nTotal: R$ ${total.toFixed(2).replace('.', ',')}`;
        message += "\n\nPor favor, me ajude a finalizar o pedido.";
        return encodeURIComponent(message);
    }

    // Lógica de manipulação de eventos

    // Adicionar item ao carrinho
    document.addEventListener('click', (event) => {
        const button = event.target.closest('.add-to-cart-btn');
        if (button) {
            const item = {
                id: button.dataset.id,
                name: button.dataset.name,
                price: parseFloat(button.dataset.price),
                image: button.dataset.image
            };
            addToCart(item);
        }
    });

    // Remover item do carrinho
    cartList.addEventListener('click', (event) => {
        const button = event.target.closest('.remove-item-btn');
        if (button) {
            const itemId = button.dataset.id;
            removeFromCart(itemId);
        }
    });

    // Enviar mensagem via WhatsApp
    sendWhatsappBtn.addEventListener('click', () => {
        const message = generateWhatsAppMessage();
        // Substitua '5511999999999' pelo seu número de WhatsApp com o código do país
        const whatsappNumber = '5571992471530'; // Deixe vazio para o usuário digitar o número ou coloque o seu
        const whatsappUrl = `https://wa.me/${whatsappNumber}?text=${message}`;
        window.open(whatsappUrl, '_blank');
    });

    // Carrega o carrinho quando a página é carregada
    window.onload = loadCart;
