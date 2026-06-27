// ============================================================
// FUNCIONES GLOBALES PARA KARDEX
// Réplica exacta de la lógica de stock que SÍ funciona
// ============================================================

function filtrarKardex(event) {
    event.preventDefault();
    
    const search = document.getElementById('searchInput')?.value || '';
    const url = search 
        ? `/api/views/kardex?search=${encodeURIComponent(search)}&tipoMovId=${document.getElementById('tipoMovSelect')?.value || ''}&page=0`
        : `/api/views/kardex?tipoMovId=${document.getElementById('tipoMovSelect')?.value || ''}&page=0`;
    
    console.log('🔍 Fetching:', url);
    
    fetch(url)
        .then(response => response.text())
        .then(html => {
            // ✅ IGUAL QUE STOCK: usar querySelector con la clase
            document.querySelector('.kardex-dashboard').outerHTML = html;
            console.log('✅ Kardex actualizado');
        })
        .catch(error => {
            console.error('❌ Error:', error);
            alert('Error al cargar los datos del kardex');
        });
}

function irAPagina(page) {
    const search = document.getElementById('searchInput')?.value || '';
    const tipoMovId = document.getElementById('tipoMovSelect')?.value || '';
    
    let url = `/api/views/kardex?page=${page}`;
    if (search) url += `&search=${encodeURIComponent(search)}`;
    if (tipoMovId) url += `&tipoMovId=${tipoMovId}`;
    
    console.log('📄 Ir a página:', page, 'URL:', url);
    
    fetch(url)
        .then(response => response.text())
        .then(html => {
            document.querySelector('.kardex-dashboard').outerHTML = html;
            console.log('✅ Página actualizada');
        })
        .catch(error => {
            console.error('❌ Error:', error);
        });
}