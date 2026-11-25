/**
 * Prueba de rendimiento – Ejercicio5MyAPIGestor
 * Herramienta: k6 (https://k6.io)
 *
 * COMANDO EXACTO PARA EJECUTAR CON DOCKER (Apuntando a tu Railway):
 *   docker run --rm -i -v "${PWD}/k6:/k6" grafana/k6 run - < k6/load-test.js
 *
 * O con Python directo (sin Docker):
 *   python k6/run-load-test.py
 */

import http from 'k6/http';
import { check, group, sleep } from 'k6';
import { Trend, Rate } from 'k6/metrics';
import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";
import { textSummary } from "https://jslib.k6.io/k6-summary/0.0.1/index.js";

// ── Configuración (Por defecto apunta a tu Backend en Railway) ───────────────
const BASE_URL = __ENV.BASE_URL || 'https://ejercicio5myapigestor-production.up.railway.app';

// Métricas personalizadas
const errorRate = new Rate('error_rate');
const latencia  = new Trend('latencia_ms', true);

// ── Opciones de carga (Escenario controlado para evaluación) ─────────────────
export const options = {
  stages: [
    { duration: '10s', target: 5 },    // Rampa de subida suave
    { duration: '20s', target: 10 },   // 10 Usuarios concurrentes (evaluación)
    { duration: '5s',  target: 0 },    // Rampa de bajada
  ],
  thresholds: {
    // Criterio de aceptación: p95 < 3000 ms sobre red pública WAN
    http_req_duration: ['p(95)<3000'],
    // Tasa de error < 1%
    error_rate: ['rate<0.01'],
  },
};

// ── Escenario principal ejecutado por cada Usuario Virtual (VU) ──────────────
export default function () {

  // 1. Consulta de usuarios en base de datos
  group('POST /buscarUsuarios', () => {
    const payload = JSON.stringify({});
    const params  = { headers: { 'Content-Type': 'application/json' } };
    const res = http.post(`${BASE_URL}/buscarUsuarios`, payload, params);
    const ok = check(res, {
      'status 200': (r) => r.status === 200,
      'body no vacío': (r) => r.body && r.body.length > 0,
    });
    errorRate.add(!ok);
    latencia.add(res.timings.duration);
  });

  sleep(0.5);

  // 2. Consulta de proyectos en base de datos
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

  sleep(0.5);

  // 3. Documentación OpenAPI / Swagger
  group('GET /v3/api-docs', () => {
    const res = http.get(`${BASE_URL}/v3/api-docs`);
    check(res, { 'status 200': (r) => r.status === 200 });
    latencia.add(res.timings.duration);
  });

  sleep(0.5);

  // 4. Telemetría Prometheus Actuator
  group('GET /actuator/prometheus', () => {
    const res = http.get(`${BASE_URL}/actuator/prometheus`);
    check(res, { 'status 200': (r) => r.status === 200 });
    latencia.add(res.timings.duration);
  });

  sleep(0.5);
}

// ── Generador automático de reporte HTML con gráficas ────────────────────────
export function handleSummary(data) {
  return {
    "k6/reporte-graficas.html": htmlReport(data),
    stdout: textSummary(data, { indent: " ", enableColors: true }),
  };
}
