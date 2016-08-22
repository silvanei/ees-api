package org.ees.api.agenda.infra.auth;

/**
 * Created by silvanei on 13/08/16.
 */
public enum Parameters {

    SLA {
        @Override
        public String toString() {
            return "sla";
        }
    },

    CLI {
        @Override
        public String toString() {
            return "cli";
        }
    },

    PERFIL {
        @Override
        public String toString() {
            return "per";
        }
    },

    SALAO_ID {
        @Override
        public String toString() {
            return "salaoId";
        }
    },

    CLIENTE_ID {
        @Override
        public String toString() {
            return "clienteId";
        }
    }
}
