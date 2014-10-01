package Default;

/**
 *
 * @author cristobal
 * @author cesar
 */
public class Node {

    private int chance;
    private String alpha;
    private Node father;
    private Node left;
    private Node right;
    private String code;

    public Node(int a, String c) {
        
        
        this.alpha = c;
        this.chance = a;
        this.left = null;
        this.right = null;
        this.father = null;
        
    }

    public int getChance() {
        return chance;
    }

    public String getAlpha() {
        return alpha;
    }

    public Node getFather() {
        return father;
    }
    
    public Node getLeftSon() {
        return left;
    }
    
    public Node getRightSon() {
        return right;
    }
    
    public String getCode(){
        return code;
    }

    public void setFather(Node father) {
        this.father = father;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }
    
    public void setCode(String code){
        this.code = code;
    }
    
    public boolean isLeaf(Node n) {
        if(n.left == null && n.right == null){
            return true;
        }
        return false;
    }

}
