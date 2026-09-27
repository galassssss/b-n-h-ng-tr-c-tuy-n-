// Quản lý giỏ hàng demo (lưu trong localStorage để hoạt động mượt mà giữa các trang)
const CartManager = {
  getCart() {
    try {
      return JSON.parse(localStorage.getItem('saleapp_cart')) || [];
    } catch (e) {
      return [];
    }
  },

  saveCart(cart) {
    localStorage.setItem('saleapp_cart', JSON.stringify(cart));
    this.updateCartUI();
  },

  addToCart(id, name, price) {
    const cart = this.getCart();
    const existing = cart.find(item => item.id === id);
    if (existing) {
      existing.quantity += 1;
    } else {
      cart.push({ id, name, price, quantity: 1 });
    }
    this.saveCart(cart);
    alert(`Đã thêm "${name}" vào giỏ hàng thành công!`);
  },

  updateCartUI() {
    const cart = this.getCart();
    const totalCount = cart.reduce((sum, item) => sum + item.quantity, 0);
    const totalPrice = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);

    const badgeEl = document.getElementById('cart-badge');
    const totalEl = document.getElementById('cart-total');

    if (badgeEl) badgeEl.textContent = totalCount;
    if (totalEl) totalEl.textContent = totalPrice.toLocaleString('vi-VN') + ' ₫';
  }
};

document.addEventListener('DOMContentLoaded', () => {
  CartManager.updateCartUI();
});
