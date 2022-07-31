package space.kovo.paster.base.dtos;

import space.kovo.paster.dtos.ErrorResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class DtoUtil {
    private static final String NEW_LINE_DELIMITER = "\r\n";

    private DtoUtil() {}

    public static List<String> getErrorMessagesList(ErrorResponseDTO dto) {
        return Optional.ofNullable(dto)
                .map(d -> {
                    List<String> result = new ArrayList<>();
                    /** form error */
                    Optional.ofNullable(d.getMessage()).ifPresent(result::add);
                    /** field errors */
                    Optional.ofNullable(d.getErrors())
                            .map(errors -> errors.values()
                                    .stream()
                                    .flatMap(v -> v.stream().map(ErrorResponseDTO::getMessage))
                                    .collect(Collectors.toList()))
                            .ifPresent(result::addAll);
                    return result;
                })
                .orElseGet(ArrayList::new);
    }

    public static String getErrorMessagesLinedText(ErrorResponseDTO dto) {
        return getErrorMessagesList(dto)
                .stream()
                .collect(Collectors.joining(NEW_LINE_DELIMITER));
    }
}
