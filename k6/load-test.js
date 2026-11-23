/**
 * Prueba de rendimiento – Ejercicio5MyAPIGestor
 * Herramienta: k6 (https://k6.io)
 *
 * Ejecutar con Docker (sin instalar k6):
 *   docker run --rm -i grafana/k6 run - < k6/load-test.js
 *
 * Para apuntar a Railway (producción):
 *   docker run --rm -i -e BASE_URL=https://TU_BACKEND.up.railway.app grafana/k6 run - < k6/load-test.js
 *
 * Guardar reporte:
 *   docker run --rm -i grafana/k6 run --out json=k6/reporte-rendimiento.json - < k6/load-test.js
 */

import http from 'k6/http';
import { check, group, sleep } from 'k6';
import { Trend, Rate } from 'k6/metrics';

// ── Configuración ────────────────────────────────────────────────────────────
const BASE_URL = __ENV.BASE_URL || 'http://localhost:8082';

// Métricas personalizadas
const errorRate = new Rate('error_rate');
const latencia  = new Trend('latencia_ms', true);

// ── Opciones de carga ────────────────────────────────────────────────────────
export const options = {
  stages: [
    { duration: '30s', target: 10 },   // Rampa de subida
    { duration: '1m',  target: 20 },   // Carga sostenida
    { duration: '10s', target: 0  },   // Rampa de bajada
  ],
  thresholds: {
    // Criterio de aceptación: p95 < 500 ms
    http_req_duration: ['p(95)<500'],
    // Tasa de error < 1%
    error_rate: ['rate<0.01'],
  },
};

// ── Escenario principal ──────────────────────────────────────────────────────
export default function () {

  group('GET /buscarUsuarios', () => {
    const res = http.get(`${BASE_URL}/buscarUsuarios`);
    const ok = check(res, {
      'status 200': (r) => r.status === 200,
      'body no vacío': (r) => r.body && r.body.length > 0,
    });
    errorRate.add(!ok);
    latencia.add(res.timings.duration);
  });

  sleep(0.5);

  group('POST /buscarProyectos', () => {
    const payload = JSON.stringify({});
    const params  = { headers: { 'Content-Type': 'application/json' } };
    const res = http.post(`${BASE_URL}/buscarProyectos`, payload, params);
    const ok = check(res, {
      'status 200': (r) => r.status === 200,
    });
    errorRate.add(!ok);
    latencia.add(res.timings.duration);
  });

  sleep(1);

  group('GET /v3/api-docs', () => {
    const res = http.get(`${BASE_URL}/v3/api-docs`);
    check(res, { 'status 200': (r) => r.status === 200 });
    latencia.add(res.timings.duration);
  });

  sleep(0.5);
}
