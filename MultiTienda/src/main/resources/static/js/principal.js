/* ═══════════════════════════════════════════════════════
     JAVASCRIPT DEL SISTEMA BASE (VERSIÓN CORREGIDA)
═══════════════════════════════════════════════════════ */

const ROLES = {
  administrador: {
    label:'Admin', color:'#2563EB', colorHover:'#1D4ED8', sidebar:'#0D1B2E',
    name:'ADMINISTRADOR', initials:'AD',
    nav:[
      { type:'section', label:'PRINCIPAL' },
      { type:'item', view:'dashboard', icon:'fas fa-tachometer-alt', label:'Dashboard' },
      { type:'item', view:'usuarios', icon:'fas fa-users', label:'Usuarios' },
      { type:'item', view:'roles', icon:'fas fa-lock', label:'Roles' },
      { type:'item', view:'tiendas', icon:'fas fa-store', label:'Tiendas' },
      { type:'item', view:'proveedores', icon:'fas fa-truck', label:'Proveedores' },
      { type:'section', label:'CATÁLOGO' },
      { type:'item', view:'categorias', icon:'fas fa-tags', label:'Categorías' },
      { type:'item', view:'productos', icon:'fas fa-box', label:'Productos' },
      { type:'item', view:'cajas', icon:'fas fa-cash-register', label:'Cajas' },
      { type:'item', view:'turnos', icon:'fas fa-clock', label:'Turnos' },
      { type:'item', view:'clientes', icon:'fas fa-user', label:'Clientes' },
      { type:'section', label:'OPERACIONES' },
      { type:'item', view:'ofertas', icon:'fas fa-tag', label:'Ofertas' },
	  { type:'item', view:'ordenes', icon:'fas fa-clipboard-list', label:'Órdenes de compra' },
      { type:'item', view:'incidencias', icon:'fas fa-exclamation-triangle', label:'Incidencias' },
      { type:'item', view:'lotes', icon:'fas fa-cubes', label:'Lotes' },
      { type:'section', label:'SISTEMA' },
      { type:'item', view:'reportes', icon:'fas fa-chart-bar', label:'Reportes' },
    ]
  },
  
  
  supervisor: {
      label:'Supervisor', color:'#D97706', colorHover:'#B45309', sidebar:'#1C1208',
      name:'SUPERVISOR', initials:'SP',
      nav:[
        { type:'section', label:'MONITOREO' },
        { type:'item', view:'dashboard', icon:'fas fa-eye', label:'Dashboard en vivo' },
        { type:'item', view:'tickets', icon:'fas fa-ticket-alt', label:'Incidencias' },
        { type:'section', label:'OPERACIONES' },
        { type:'item', view:'cajas', icon:'fas fa-cash-register', label:'Cajas' },
        { type:'item', view:'devoluciones', icon:'fas fa-undo', label:'Devoluciones' },
        { type:'item', view:'ofertas', icon:'fas fa-tags', label:'Ofertas' },
		
        { type:'item', view:'stock', icon:'fas fa-boxes', label:'Stock' },
        { type:'section', label:'AUTORIZACIONES' },
        { type:'item', view:'autorizar', icon:'fas fa-check-circle', label:'Autorizar' },
        { type:'section', label:'REPORTES' },
        { type:'item', view:'reportes', icon:'fas fa-chart-line', label:'Reportes' },
      ]
    },

  
  
  
  almacenero: {
    label:'Almacén', color:'#059669', colorHover:'#047857', sidebar:'#021F14',
    name:'Carlos Ruiz', role:'Almacenero', initials:'CR',
    nav:[
      { type:'section', label:'PRINCIPAL' },
      { type:'item', view:'dashboard', icon:'fas fa-tachometer-alt', label:'Dashboard' },
      { type:'item', view:'validar', icon:'fas fa-check-double', label:'Validar productos', badge:'5' },
      { type:'item', view:'recibir', icon:'fas fa-truck-loading', label:'Recibir mercadería' },
      { type:'section', label:'INVENTARIO' },
      { type:'item', view:'stock', icon:'fas fa-boxes', label:'Stock actual' },
      { type:'item', view:'kardex', icon:'fas fa-book', label:'Kardex' },
      { type:'item', view:'mermas', icon:'fas fa-trash', label:'Mermas' },
      { type:'section', label:'ANÁLISIS' },
      { type:'item', view:'reportes', icon:'fas fa-chart-bar', label:'Reportes' },
    ]
  },
  proveedor: {
    label:'Proveedor', color:'#7C3AED', colorHover:'#6D28D9', sidebar:'#1A0A40',
    name:'PROVEEDOR', initials:'PV',
    nav:[
      { type:'section', label:'MI PORTAL' },
      { type:'item', view:'dashboard', icon:'fas fa-tachometer-alt', label:'Dashboard' },
      { type:'group', icon:'fas fa-box', label:'Mis productos', children:[
        { view:'productos', label:'Ver mis productos' },
        { view:'subir', label:'Subir nuevo' },
      ]},
      { type:'section', label:'OPERACIONES' },
      { type:'item', view:'ordenes', icon:'fas fa-clipboard-list', label:'Órdenes' },
      { type:'item', view:'entregas', icon:'fas fa-truck', label:'Entregas' },
      { type:'item', view:'facturacion', icon:'fas fa-file-invoice', label:'Facturación' },
      { type:'item', view:'pagos', icon:'fas fa-dollar-sign', label:'Pagos' },
      { type:'section', label:'CUENTA' },
      { type:'item', view:'perfil', icon:'fas fa-user-circle', label:'Mi perfil' },
    ]
  },
  cajero: {
    label:'POS', color:'#16A34A', colorHover:'#15803D', sidebar:'#071A0E',
    name:'Juan Pérez', role:'Cajero', initials:'JP',
    nav:[
      { type:'item', view:'apertura', icon:'fas fa-cash-register', label:'Apertura' },
      { type:'item', view:'venta', icon:'fas fa-shopping-cart', label:'Nueva venta' },
      { type:'item', view:'misventas', icon:'fas fa-chart-line', label:'Mis ventas' },
      { type:'item', view:'devolucion', icon:'fas fa-undo-alt', label:'Devolución' },
      { type:'item', view:'cierre', icon:'fas fa-lock', label:'Cierre' },
    ]
  }
};





function cerrarSesion() {
    window.location.href = '/login';
}

/* ── ESTADO ────────────────────────────────────────────── */
const S = {
  role: 'admin',
  view: 'dashboard',
  collapsed: false,
  dark: false,
  openGroups: new Set(),
};

const NOTIFS = [
  { id:1, unread:true, icon:'fa-exclamation-triangle', title:'Stock crítico — Tienda Norte', sub:'Azúcar 1kg: 3 unidades restantes', color:'#F59E0B', bg:'#FEF3C7' },
  { id:2, unread:true, icon:'fa-bell', title:'Nueva orden #OC-2841 recibida', sub:'Lácteos del Norte · S/ 1,240', color:'#3B82F6', bg:'#DBEAFE' },
  { id:3, unread:false, icon:'fa-check-circle', title:'Yogurt Gloria 1L aprobado', sub:'Ya disponible en el catálogo', color:'#10B981', bg:'#D1FAE5' },
];

/* ── INIT ──────────────────────────────────────────────── */
document.addEventListener('DOMContentLoaded', () => {
    const r = window.USER_ROLE || localStorage.getItem('tvp-role') || 'admin';
    setRole(r);
    
    // Solo construir notificaciones si existe el contenedor
    const notifList = document.getElementById('notifList');
    if (notifList) {
        buildNotifs();
    }
});

/* ── SET ROLE (CON VERIFICACIONES) ────────────────────── */
function setRole(role) {
    S.role = role;
    const cfg = ROLES[role];
    if (!cfg) return;

    const root = document.documentElement;
    root.style.setProperty('--role-primary', cfg.color);
    root.style.setProperty('--role-primary-hover', cfg.colorHover);
    root.style.setProperty('--role-sidebar', cfg.sidebar);
    root.style.setProperty('--role-sidebar-active', cfg.color);

    // Verificar cada elemento antes de modificarlo
    const sidebarAvatar = document.getElementById('sidebarAvatar');
    if (sidebarAvatar) {
        sidebarAvatar.textContent = cfg.initials;
        sidebarAvatar.style.background = cfg.color;
    }
    
    const topbarAvatar = document.getElementById('topbarAvatar');
    if (topbarAvatar) {
        topbarAvatar.textContent = cfg.initials;
        topbarAvatar.style.background = cfg.color;
    }
    
    const sidebarUserName = document.getElementById('sidebarUserName');
    if (sidebarUserName) sidebarUserName.textContent = cfg.name;
    
    const sidebarUserRole = document.getElementById('sidebarUserRole');
    if (sidebarUserRole) sidebarUserRole.textContent = cfg.role;
    
    const topbarName = document.getElementById('topbarName');
    if (topbarName) topbarName.textContent = cfg.name;
    
    const topbarRole = document.getElementById('topbarRole');
    if (topbarRole) topbarRole.textContent = cfg.role;
    
    const roleBadge = document.getElementById('roleBadge');
    if (roleBadge) roleBadge.textContent = cfg.label;
    
    const logoIcon = document.getElementById('logoIcon');
    if (logoIcon) logoIcon.innerHTML = '<i class="fas fa-store"></i>';

    // Solo construir nav si existe el sidebar
    const sidebarNav = document.getElementById('sidebarNav');
    if (sidebarNav) {
        buildNav(cfg.nav);
        const first = cfg.nav.find(n => n.type === 'item' || (n.type === 'group' && n.children?.length));
        if (first) {
            const v = first.type === 'item' ? first.view : first.children[0].view;
            loadView(v);
        }
    }
    
    localStorage.setItem('tvp-role', role);
}

/* ── BUILD NAV (CON VERIFICACIÓN) ────────────────────── */
function buildNav(items) {
    const nav = document.getElementById('sidebarNav');
    if (!nav) return; // Salir si no existe el sidebar
    
    nav.innerHTML = items.map((item, idx) => {
        if (item.type === 'section') {
            return `<div class="nav-section-label">${item.label}</div>`;
        }
        if (item.type === 'item') {
            const badge = item.badge ? `<span class="nav-badge">${item.badge}</span>` : '';
            return `
                <div class="nav-item" data-view="${item.view}" data-tooltip="${item.label}"
                     onclick="loadView('${item.view}')">
                    <i class="${item.icon} nav-icon"></i>
                    <span class="nav-label">${item.label}</span>
                    ${badge}
                </div>`;
        }
        if (item.type === 'group') {
            const gId = 'grp-' + idx;
            const badge = item.badge ? `<span class="nav-badge">${item.badge}</span>` : '';
            const subs = item.children.map(c => {
                const sb = c.badge ? `<span class="nav-badge" style="margin-left:auto">${c.badge}</span>` : '';
                return `<div class="nav-sub-item" data-view="${c.view}" onclick="loadView('${c.view}')">${c.label}${sb}</div>`;
            }).join('');
            return `
                <div class="nav-item" data-group="${gId}" data-tooltip="${item.label}"
                     onclick="toggleGroup('${gId}')">
                    <i class="${item.icon} nav-icon"></i>
                    <span class="nav-label">${item.label}</span>
                    ${badge}
                    <i class="fas fa-chevron-right nav-chevron"></i>
                </div>
                <div class="nav-sub" id="${gId}">${subs}</div>`;
        }
        return '';
    }).join('');
}

/* ── LOAD VIEW ─────────────────────────────────────────── */
async function loadView(viewName) {
    S.view = viewName;
    
    // Verificar elementos antes de usarlos
    document.querySelectorAll('.nav-item, .nav-sub-item').forEach(el => {
        el.classList.toggle('active', el.getAttribute('data-view') === viewName);
    });
    
    const bcCurrent = document.getElementById('bcCurrent');
    if (bcCurrent) bcCurrent.textContent = findLabel(viewName);
    
    showLoader();
    try {
        const response = await fetch(`/api/views/${viewName}`);
        if (!response.ok) throw new Error("No se pudo cargar la vista");
        const html = await response.text();
        document.getElementById('mainContent').innerHTML = html;

        const scripts = document.getElementById('mainContent').querySelectorAll('script');
        scripts.forEach(script => {
            const newScript = document.createElement('script');
            if (script.src) {
                newScript.src = script.src;
            } else {
                newScript.textContent = script.textContent;
            }
            document.body.appendChild(newScript);
        });

        ejecutarCargaDatos(viewName);
    } catch (error) {
        console.error("Error cargando vista:", error);
        document.getElementById('mainContent').innerHTML = defaultView(viewName);
    } finally {
        hideLoader();
    }
}

function findLabel(viewName) {
    const cfg = ROLES[S.role];
    if (!cfg) return viewName;
    for (const item of cfg.nav) {
        if (item.type === 'item' && item.view === viewName) return item.label;
        if (item.type === 'group' && item.children) {
            for (const c of item.children) if (c.view === viewName) return item.label + ' · ' + c.label;
        }
    }
    return viewName.charAt(0).toUpperCase() + viewName.slice(1);
}

function defaultView(name) {
    return `
    <div class="page-header">
        <div>
            <div class="page-title">${name.charAt(0).toUpperCase()+name.slice(1)}</div>
            <div class="page-subtitle">Sección en construcción</div>
        </div>
    </div>
    <div class="card">
        <div class="empty-state">
            <i class="fas fa-hammer"></i>
            <h3>Vista en desarrollo</h3>
            <p>Esta sección está siendo construida. Vuelve pronto.</p>
        </div>
    </div>`;
}

function toggleGroup(gId) {
    const sub = document.getElementById(gId);
    const btn = document.querySelector(`[data-group="${gId}"]`);
    if (!sub || !btn) return;
    const isOpen = sub.classList.contains('open');
    document.querySelectorAll('.nav-sub.open').forEach(el => el.classList.remove('open'));
    document.querySelectorAll('.nav-item.accordion-open').forEach(el => el.classList.remove('accordion-open'));
    if (!isOpen) {
        sub.classList.add('open');
        btn.classList.add('accordion-open');
    }
}

function ejecutarCargaDatos(viewName) {
    setTimeout(() => {
        switch(viewName) {
            case 'usuarios': if (typeof cargarUsuarios === 'function') cargarUsuarios(); break;
            case 'roles': if (typeof cargarRoles === 'function') cargarRoles(); break;
            case 'tiendas': if (typeof cargarTiendas === 'function') cargarTiendas(); break;
            case 'proveedores': if (typeof cargarProveedores === 'function') cargarProveedores(); break;
            case 'categorias': if (typeof cargarCategorias === 'function') cargarCategorias(); break;
            case 'productos': if (typeof cargarProductos === 'function') cargarProductos(); break;
            case 'cajas': if (typeof cargarCajas === 'function') cargarCajas(); break;
            case 'turnos': if (typeof cargarTurnos === 'function') cargarTurnos(); break;
            case 'clientes': if (typeof cargarClientes === 'function') cargarClientes(); break;
            case 'ofertas': if (typeof cargarOfertas === 'function') cargarOfertas(); break;
            case 'incidencias': if (typeof cargarIncidencias === 'function') cargarIncidencias(); break;
            case 'mermas': if (typeof cargarMermas === 'function') cargarMermas(); break;
            case 'transferencias': if (typeof cargarTransferencias === 'function') cargarTransferencias(); break;
            case 'lotes': if (typeof cargarLotes === 'function') cargarLotes(); break;
            case 'dashboard': if (typeof cargarDashboard === 'function') cargarDashboard(); break;
            // 🔥 AGREGAR REPORTES
            case 'reportes': if (typeof cargarReportes === 'function') cargarReportes(); break;
            default: break;
        }
    }, 100);
}

function toggleSidebar() {
    const shell = document.getElementById('appShell');
    if (!shell) return;
    if (window.innerWidth <= 640) {
        shell.classList.toggle('mobile-open');
    } else {
        S.collapsed = !S.collapsed;
        shell.classList.toggle('sidebar-collapsed', S.collapsed);
    }
}
/* ── DARK MODE ─────────────────────────────────────────── */
function toggleDark() {
  S.dark = !S.dark;
  if (S.dark) applyDark(true); else removeDark();
}
function applyDark(save) {
  document.documentElement.setAttribute('data-theme','dark');
  const ic = document.getElementById('darkIcon');
  if (ic) { ic.classList.remove('fa-moon'); ic.classList.add('fa-sun'); }
  S.dark = true;
  if (save) localStorage.setItem('tvp-dark','true');
}
function removeDark() {
  document.documentElement.setAttribute('data-theme','light');
  const ic = document.getElementById('darkIcon');
  if (ic) { ic.classList.remove('fa-sun'); ic.classList.add('fa-moon'); }
  S.dark = false;
  localStorage.setItem('tvp-dark','false');
}

/* ── PROFILE DROPDOWN ─────────────────────────────────── */
function toggleProfileDd(e) {
  e.stopPropagation();
  const dd = document.getElementById('profileDd');
  const tp = document.getElementById('topbarProfile');
  const isOpen = dd.classList.contains('open');
  closeAll();
  if (!isOpen) { dd.classList.add('open'); tp.classList.add('open'); }
}
document.addEventListener('click', closeAll);
function closeAll() {
  document.getElementById('profileDd')?.classList.remove('open');
  document.getElementById('topbarProfile')?.classList.remove('open');
  document.getElementById('notifPanel')?.classList.remove('open');
}

/* ── NOTIFICACIONES ────────────────────────────────────── */
function buildNotifs() {
  const list = document.getElementById('notifList');
  const dot = document.getElementById('notifDot');
  const unread = NOTIFS.filter(n => n.unread);
  dot.classList.toggle('visible', unread.length > 0);
  list.innerHTML = NOTIFS.map(n => `
    <div class="notif-item ${n.unread?'unread':''}">
      <div class="n-icon" style="background:${n.bg};color:${n.color}"><i class="fas ${n.icon}"></i></div>
      <div>
        <div class="n-title">${n.title}</div>
        <div class="n-sub">${n.sub}</div>
      </div>
    </div>`).join('');
}
function toggleNotif(e) {
  e.stopPropagation();
  const p = document.getElementById('notifPanel');
  const isOpen = p.classList.contains('open');
  closeAll();
  if (!isOpen) p.classList.add('open');
}
function markAllRead() {
  NOTIFS.forEach(n => n.unread = false);
  buildNotifs();
  showToast('success','Listo','Todas las notificaciones marcadas como leídas.');
}

/* ── LOADER ─────────────────────────────────────────────── */
function showLoader() { document.getElementById('pageLoader').classList.add('visible'); }
function hideLoader() { document.getElementById('pageLoader').classList.remove('visible'); }

/* ── TOASTS ─────────────────────────────────────────────── */
const TOAST_ICONS = { success:'fa-check-circle', warning:'fa-exclamation-triangle', error:'fa-times-circle', info:'fa-info-circle' };
function showToast(type='info', title='', msg='', dur=3400) {
    const id = 'toast-' + Date.now();
    const el = document.createElement('div');
    el.className = `toast ${type}`;
    el.id = id;
    el.innerHTML = `
        <div class="t-icon"><i class="fas ${TOAST_ICONS[type] || TOAST_ICONS.info}"></i></div>
        <div class="t-body">
            <div class="t-title">${title}</div>
            ${msg ? `<div class="t-msg">${msg}</div>` : ''}
        </div>
        <i class="fas fa-times t-close" onclick="removeToast('${id}')"></i>
    `;

    // 🔥 USAR EL CONTENEDOR GLOBAL
    const container = document.getElementById('toastContainerGlobal');
    if (container) {
        container.appendChild(el);
        setTimeout(() => removeToast(id), dur);
    } else {
        console.warn('⚠️ Contenedor de toasts no encontrado');
        // Fallback: alert
        alert(title + ': ' + msg);
    }
}
function removeToast(id) {
  const el = document.getElementById(id);
  if (!el) return;
  el.classList.add('removing');
  setTimeout(() => el.remove(), 260);
}

/* ── MODAL ──────────────────────────────────────────────── */
function openConfirm(title, msg, onOk) {
  setText('confirmTitle', title);
  setText('confirmMsg', msg);
  document.getElementById('confirmBtn').onclick = () => { onOk(); closeModal('confirmModal'); };
  document.getElementById('confirmModal').classList.add('open');
}
function closeModal(id) { document.getElementById(id)?.classList.remove('open'); }
document.addEventListener('keydown', e => {
  if (e.key === 'Escape') { closeAll(); closeModal('confirmModal'); }
});

/* ── TOGGLE SWITCH ─────────────────────────────────────── */
function toggleSwitch(el) {
  el.querySelector('.toggle-track')?.classList.toggle('on');
}

/* ── HELPERS ────────────────────────────────────────────── */
function setText(id, txt) { const el=document.getElementById(id); if(el) el.textContent=txt; }

// ============================================================
// ALIAS PARA CARGAR PÁGINAS DESDE EL DASHBOARD
// ============================================================
function cargarPagina(viewName) {
    loadView(viewName);
}
