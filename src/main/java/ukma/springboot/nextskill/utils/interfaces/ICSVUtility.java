package ukma.springboot.nextskill.utils.interfaces;

import ukma.springboot.nextskill.utils.AbstractCSVConvertable;

import java.util.List;

public interface ICSVUtility {
    void saveTo(String directory, String fileBasename, List<? extends AbstractCSVConvertable> list);
}
