public class Main {
    public static void main(String[] args) {
        Pedido pedido = new Pedido(100.0, new DescontoClienteVip());
        ProcessadorDePedidos processador = new ProcessadorDePedidos(new EmailNotificador());
        processador.processar(pedido);
    }


    static class Pedido {
        private double valor;
        private DescontoStrategy desconto;

        public Pedido(double valor, DescontoStrategy desconto) {
            this.valor = valor;
            this.desconto = desconto;
        }

        public double calcularValorComDesconto() {
            return valor - desconto.aplicarDesconto(valor);
        }
    }


    interface DescontoStrategy {
        double aplicarDesconto(double valor);
    }


    static class DescontoClienteComum implements DescontoStrategy {
        @Override
        public double aplicarDesconto(double valor) {
            return valor * 0.05;
        }
    }

    static class DescontoClienteVip implements DescontoStrategy {
        @Override
        public double aplicarDesconto(double valor) {
            return valor * 0.20;
        }
    }


    interface Notificador {
        void notificar(String mensagem);
    }


    static class EmailNotificador implements Notificador {
        @Override
        public void notificar(String mensagem) {
            System.out.println("Enviando e-mail: " + mensagem);
        }
    }

    static class SmsNotificador implements Notificador {
        @Override
        public void notificar(String mensagem) {
            System.out.println("Enviando SMS: " + mensagem);
        }
    }

   
    static class ProcessadorDePedidos {
        private Notificador notificador;

        public ProcessadorDePedidos(Notificador notificador) {
            this.notificador = notificador;
        }

        public void processar(Pedido pedido) {
            double valorFinal = pedido.calcularValorComDesconto();
            System.out.println("Pedido processado! Valor final: " + valorFinal);
            notificador.notificar("Seu pedido foi concluído.");
        }
    }
}
