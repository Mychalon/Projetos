package model;

import java.awt.*;

public class WrapLayout extends FlowLayout {

    public WrapLayout() {
        super();
    }

    public WrapLayout(int align) {
        super(align);
    }

    public WrapLayout(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
    }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }

    @Override
    public Dimension minimumLayoutSize(Container target) {
        return layoutSize(target, false);
    }

    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {
            int width = target.getWidth();
            if (width == 0) width = Integer.MAX_VALUE;

            Insets insets = target.getInsets();
            int maxWidth = width - (insets.left + insets.right);
            int x = 0, y = insets.top, rowHeight = 0;
            int nmembers = target.getComponentCount();

            for (int i = 0; i < nmembers; i++) {
                Component m = target.getComponent(i);
                if (!m.isVisible()) continue;
                Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();

                if ((x == 0) || ((x + d.width) <= maxWidth)) {
                    if (x > 0) x += getHgap();
                    x += d.width;
                    rowHeight = Math.max(rowHeight, d.height);
                } else {
                    x = d.width;
                    y += rowHeight + getVgap();
                    rowHeight = d.height;
                }
            }
            y += rowHeight;
            return new Dimension(width, y + insets.bottom);
        }
    }
}

