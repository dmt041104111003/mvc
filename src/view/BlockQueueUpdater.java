//package view;
//import controller.logic;
//
//class BlockQueueUpdater extends Thread {
//    private final frame parent;
//    private final logic game;
//
//    public BlockQueueUpdater(frame parent, logic game) {
//        this.parent = parent;
//        this.game = game;
//    }
//
//    @Override
//    public void run() {
//        while (game.isRunning()) {
//            parent.updateQueue(); 
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}