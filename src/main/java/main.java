import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        List<List<Integer>> tree = new ArrayList<>();
        Path path = Paths.get(main.class.getClassLoader()
                .getResource("input.txt").toURI());

        Stream<String> lines = Files.lines(path);
        lines.forEach(l -> {
            tree.add(Stream.of(l.split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList()));
        });
        lines.close();

        Node root = new Node(tree.get(0).get(0));
        Utils t = new Utils();
        t.setPath(tree, 0, 0, root);
        System.out.println(t.findMaxSum(root));
    }

}

class Utils {

    public Utils() {
    }

    public Node setPath(List<List<Integer>> tree, int depth, int i, Node c) {
        if (tree.size() == depth + 1) {
            return c;
        }
        if (tree.get(depth).get(i) % 2 == 0) {
            if (tree.get(depth + 1) != null) {
                if (tree.get(depth + 1).get(i) != null) {
                    if (tree.get(depth + 1).get(i) % 2 != 0) {
                        c.l = new Node(tree.get(depth + 1).get(i));
                        setPath(tree, depth + 1, i, c.l);
                    }
                }
                if (tree.get(depth + 1).get(i + 1) != null) {
                    if (tree.get(depth + 1).get(i + 1) % 2 != 0) {
                        c.r = new Node(tree.get(depth + 1).get(i + 1));
                        setPath(tree, depth + 1, i + 1, c.r);
                    }
                }
            }
        } else {
            if (tree.get(depth + 1) != null) {
                if (tree.get(depth + 1).get(i) != null) {
                    if (tree.get(depth + 1).get(i) % 2 == 0) {
                        c.l = new Node(tree.get(depth + 1).get(i));
                        setPath(tree, depth + 1, i, c.l);
                    }
                    if (tree.get(depth + 1).get(i + 1) != null) {
                        if (tree.get(depth + 1).get(i + 1) % 2 == 0) {
                            c.r = new Node(tree.get(depth + 1).get(i + 1));
                            setPath(tree, depth + 1, i + 1, c.r);
                        }
                    }
                }
            }
        }
        return c;
    }

    int findMaxSum(Node node) {
        if (node == null)
            return 0;

        int l = findMaxSum(node.l);
        int r = findMaxSum(node.r);
        return Math.max(l, r) + node.v;
    }
}

class Node {
    int v;
    Node l, r;

    public Node(int v) {
        this.v = v;
        l = r = null;
    }
}