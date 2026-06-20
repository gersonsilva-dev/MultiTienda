/* ═══════════════════════════════════════════════════════
     JAVASCRIPT DEL SISTEMA BASE
═══════════════════════════════════════════════════════ */
/* ── CONFIGURACIÓN DE ROLES ──────────────────────────────
   Para usar en cada página hija:
     setRole('supervisor');
   Los colores, usuario y menú cambian automáticamente.   */

const ROLES = {
  administrador: {
    label:'Admin', color:'#2563EB', colorHover:'#1D4ED8', sidebar:'#0D1B2E',
    name:'Alan Díaz', role:'Administrador Global', initials:'AD',
    nav:[
      { type:'section', label:'PRINCIPAL' },
      { type:'item',    view:'dashboard',     icon:'fas fa-tachometer-alt', label:'Dashboard' },
      { type:'group',   icon:'fas fa-store',  label:'Tiendas', children:[
        { view:'tiendas-list',  label:'Ver tiendas' },
        { view:'tiendas-new',   label:'Nueva tienda' },
      ]},
      { type:'group',   icon:'fas fa-users',  label:'Empleados', children:[
        { view:'empleados',     label:'Listado' },
        { view:'empleados-new', label:'Agregar empleado' },
      ]},
      { type:'group',   icon:'fas fa-user',   label:'Clientes', children:[
        { view:'clientes',     label:'Listado' },
        { view:'clientes-new', label:'Registrar cliente' },
      ]},
      { type:'section', label:'CATÁLOGO' },
      { type:'group',   icon:'fas fa-box',    label:'Productos', badge:'5', children:[
        { view:'productos',        label:'Catálogo' },
        { view:'productos-aprobar',label:'Por aprobar', badge:'5' },
      ]},
      { type:'item',    view:'proveedores', icon:'fas fa-truck',    label:'Proveedores' },
      { type:'group',   icon:'fas fa-tag',   label:'Ofertas', children:[
        { view:'ofertas',     label:'Ofertas activas' },
        { view:'ofertas-new', label:'Crear oferta' },
      ]},
      { type:'section', label:'SISTEMA' },
      { type:'item',    view:'reportes',      icon:'fas fa-chart-bar',  label:'Reportes' },
      { type:'item',    view:'auditoria',     icon:'fas fa-shield-alt', label:'Auditoría' },
      { type:'item',    view:'configuracion', icon:'fas fa-cog',        label:'Configuración' },
    ]
  },
  supervisor: {
    label:'Supervisor', color:'#D97706', colorHover:'#B45309', sidebar:'#1C1208',
    name:'Rosa López', role:'Supervisora', initials:'RL',
    nav:[
      { type:'section', label:'MONITOREO' },
      { type:'item', view:'dashboard',  icon:'fas fa-eye',          label:'Dashboard en vivo' },
      { type:'item', view:'autorizar',  icon:'fas fa-check-circle', label:'Autorizar', badge:'3' },
      { type:'item', view:'tickets',    icon:'fas fa-ticket-alt',   label:'Tickets', badge:'2' },
      { type:'section', label:'OPERACIONES' },
      { type:'item', view:'stock',    icon:'fas fa-boxes',     label:'Stock crítico', badge:'8' },
      { type:'item', view:'reportes', icon:'fas fa-chart-line', label:'Reportes' },
    ]
  },
  almacenero: {
    label:'Almacén', color:'#059669', colorHover:'#047857', sidebar:'#021F14',
    name:'Carlos Ruiz', role:'Almacenero', initials:'CR',
    nav:[
      { type:'section', label:'PRINCIPAL' },
      { type:'item', view:'dashboard', icon:'fas fa-tachometer-alt', label:'Dashboard' },
      { type:'item', view:'validar',   icon:'fas fa-check-double',   label:'Validar productos', badge:'5' },
      { type:'item', view:'recibir',   icon:'fas fa-truck-loading',  label:'Recibir mercadería' },
      { type:'section', label:'INVENTARIO' },
      { type:'item', view:'stock',    icon:'fas fa-boxes',   label:'Stock actual' },
      { type:'item', view:'kardex',   icon:'fas fa-book',    label:'Kardex' },
      { type:'item', view:'mermas',   icon:'fas fa-trash',   label:'Mermas' },
      { type:'section', label:'ANÁLISIS' },
      { type:'item', view:'reportes', icon:'fas fa-chart-bar', label:'Reportes' },
    ]
  },
  proveedor: {
    label:'Proveedor', color:'#7C3AED', colorHover:'#6D28D9', sidebar:'#1A0A40',
    name:'Lácteos del Norte', role:'Proveedor', initials:'LN',
    nav:[
      { type:'section', label:'MI PORTAL' },
      { type:'item', view:'dashboard',  icon:'fas fa-tachometer-alt', label:'Dashboard' },
      { type:'group', icon:'fas fa-box', label:'Mis productos', children:[
        { view:'productos',    label:'Ver mis productos' },
        { view:'subir',        label:'Subir nuevo' },
      ]},
      { type:'section', label:'OPERACIONES' },
      { type:'item', view:'ordenes',     icon:'fas fa-clipboard-list', label:'Órdenes', badge:'3' },
      { type:'item', view:'entregas',    icon:'fas fa-truck',          label:'Entregas' },
      { type:'item', view:'facturacion', icon:'fas fa-file-invoice',   label:'Facturación' },
      { type:'item', view:'pagos',       icon:'fas fa-dollar-sign',    label:'Pagos' },
      { type:'section', label:'CUENTA' },
      { type:'item', view:'perfil', icon:'fas fa-user-circle', label:'Mi perfil' },
    ]
  },
  cajero: {
    label:'POS', color:'#16A34A', colorHover:'#15803D', sidebar:'#071A0E',
    name:'Juan Pérez', role:'Cajero · Caja 1', initials:'JP',
    nav:[
      { type:'item', view:'venta',       icon:'fas fa-shopping-cart',  label:'Nueva venta' },
      { type:'item', view:'suspendidas', icon:'fas fa-pause-circle',   label:'Suspendidas', badge:'2' },
      { type:'item', view:'ofertas',     icon:'fas fa-tag',            label:'Ofertas activas', badge:'4' },
      { type:'item', view:'misventas',   icon:'fas fa-chart-line',     label:'Mis ventas' },
      { type:'item', view:'devolucion',  icon:'fas fa-undo-alt',       label:'Devolución' },
    ]
  }
};

/* ── ESTADO ────────────────────────────────────────────── */
const S = {
  role: 'admin',
  view: 'dashboard',
  collapsed: false,
  dark: false,
  openGroups: new Set(),   // IDs de grupos abiertos en accordion
};

const NOTIFS = [
  { id:1, unread:true,  icon:'fa-exclamation-triangle', title:'Stock crítico — Tienda Norte',  sub:'Azúcar 1kg: 3 unidades restantes',    color:'#F59E0B', bg:'#FEF3C7' },
  { id:2, unread:true,  icon:'fa-bell',                 title:'Nueva orden #OC-2841 recibida', sub:'Lácteos del Norte · S/ 1,240',         color:'#3B82F6', bg:'#DBEAFE' },
  { id:3, unread:false, icon:'fa-check-circle',         title:'Yogurt Gloria 1L aprobado',     sub:'Ya disponible en el catálogo',         color:'#10B981', bg:'#D1FAE5' },
];

/* ── INIT ──────────────────────────────────────────────── */
document.addEventListener('DOMContentLoaded', () => {
	// Si window.USER_ROLE existe (inyectado por Thymeleaf), úsalo.
	    // Si no, intenta obtenerlo de localStorage o pon 'admin' por defecto.
	    const r = window.USER_ROLE || localStorage.getItem('tvp-role') || 'admin';
	    
	    setRole(r);
	    buildNotifs();
	});
/* ── SET ROLE ──────────────────────────────────────────── */
function setRole(role) {
  S.role = role;
  const cfg = ROLES[role];
  if (!cfg) return;

  // CSS vars de color
  const root = document.documentElement;
  root.style.setProperty('--role-primary',        cfg.color);
  root.style.setProperty('--role-primary-hover',  cfg.colorHover);
  root.style.setProperty('--role-sidebar',        cfg.sidebar);
  root.style.setProperty('--role-sidebar-active', cfg.color);

  // Textos
  ['sidebarAvatar','topbarAvatar'].forEach(id => {
    const el = document.getElementById(id);
    if (!el) return;
    el.textContent = cfg.initials;
    el.style.background = cfg.color;
  });
  setText('sidebarUserName', cfg.name);
  setText('sidebarUserRole', cfg.role);
  setText('topbarName',      cfg.name);
  setText('topbarRole',      cfg.role);
  setText('roleBadge',       cfg.label);
  setText('logoIcon', '');
  document.getElementById('logoIcon').innerHTML = '<i class="fas fa-store"></i>';

  // Construir menú
  buildNav(cfg.nav);

  // Cargar primera vista
  const first = cfg.nav.find(n => n.type === 'item' || (n.type === 'group' && n.children?.length));
  if (first) {
    const v = first.type === 'item' ? first.view : first.children[0].view;
    loadView(v);
  }
  localStorage.setItem('tvp-role', role);
}

/* ── BUILD NAV ─────────────────────────────────────────── */
function buildNav(items) {
  const nav = document.getElementById('sidebarNav');
  nav.innerHTML = items.map((item, idx) => {
    if (item.type === 'section') {
      return `<div class="nav-section-label">${item.label}</div>`;
    }
    if (item.type === 'item') {
      const badge = item.badge ? `<span class="nav-badge">${item.badge}</span>` : '';
      return `
        <div class="nav-item" data-view="${item.view}" data-tooltip="${item.label}"
             onclick="loadView('${item.view}')">
          <i class="${item.icon} nav-icon" aria-hidden="true"></i>
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
          <i class="${item.icon} nav-icon" aria-hidden="true"></i>
          <span class="nav-label">${item.label}</span>
          ${badge}
          <i class="fas fa-chevron-right nav-chevron"></i>
        </div>
        <div class="nav-sub" id="${gId}">${subs}</div>`;
    }
    return '';
  }).join('');
}

/* ── ACCORDION GROUP ───────────────────────────────────── */
function toggleGroup(gId) {
  const sub = document.getElementById(gId);
  const btn = document.querySelector(`[data-group="${gId}"]`);
  if (!sub || !btn) return;
  const isOpen = sub.classList.contains('open');
  // Cerrar todos los grupos
  document.querySelectorAll('.nav-sub.open').forEach(el => el.classList.remove('open'));
  document.querySelectorAll('.nav-item.accordion-open').forEach(el => el.classList.remove('accordion-open'));
  // Abrir este si estaba cerrado
  if (!isOpen) {
    sub.classList.add('open');
    btn.classList.add('accordion-open');
  }
}

/* ── LOAD VIEW ─────────────────────────────────────────── */
/* ── LOAD VIEW MODIFICADO PARA FETCH ─────────────────────── */
async function loadView(viewName) {
    S.view = viewName;

    // 1. Actualizar activos en el menú
    document.querySelectorAll('.nav-item, .nav-sub-item').forEach(el => {
        el.classList.toggle('active', el.getAttribute('data-view') === viewName);
    });

    // 2. Breadcrumb
    setText('bcCurrent', findLabel(viewName));

    // 3. Carga desde el Servidor
    showLoader();
    try {
        // La URL coincide con tu @RequestMapping("/api/views") en el Controller
        const response = await fetch(`/api/views/${viewName}`);
        
        if (!response.ok) throw new Error("No se pudo cargar la vista");
        
        const html = await response.text();
        document.getElementById('mainContent').innerHTML = html;
        
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

/* ── SIDEBAR ────────────────────────────────────────────── */
function toggleSidebar() {
  const shell = document.getElementById('appShell');
  if (window.innerWidth <= 640) {
    shell.classList.toggle('mobile-open');
  } else {
    S.collapsed = !S.collapsed;
    shell.classList.toggle('sidebar-collapsed', S.collapsed);
  }
}
document.getElementById('mobileOverlay').addEventListener('click', () => {
  document.getElementById('appShell').classList.remove('mobile-open');
});

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
  const dot  = document.getElementById('notifDot');
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
function hideLoader()  { document.getElementById('pageLoader').classList.remove('visible'); }

/* ── TOASTS ─────────────────────────────────────────────── */
const TOAST_ICONS = { success:'fa-check-circle', warning:'fa-exclamation-triangle', error:'fa-times-circle', info:'fa-info-circle' };
function showToast(type='info', title='', msg='', dur=3400) {
  const id = 'toast-'+Date.now();
  const el = document.createElement('div');
  el.className = `toast ${type}`; el.id = id;
  el.innerHTML = `
    <div class="t-icon"><i class="fas ${TOAST_ICONS[type]||TOAST_ICONS.info}"></i></div>
    <div class="t-body">
      <div class="t-title">${title}</div>
      ${msg ? `<div class="t-msg">${msg}</div>` : ''}
    </div>
    <i class="fas fa-times t-close" onclick="removeToast('${id}')"></i>`;
  document.getElementById('toast-container').appendChild(el);
  setTimeout(() => removeToast(id), dur);
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

/* ── DEMO ───────────────────────────────────────────────── */
/* Para cambiar de rol en una página hija:
     setRole('supervisor');
   Para registrar vistas propias:
     window.VIEWS = { dashboard: '<div>...</div>', ... };
*/
setTimeout(() => showToast('success','Layout Master v2.0 listo','Sidebar, accordion, dark mode y toasts funcionando.'), 500);