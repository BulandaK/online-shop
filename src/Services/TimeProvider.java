package Services;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Provides a centralized and consistent source of time for the application.
 * <p>
 * This class abstracts the system clock and enforces a specific time zone (UTC)
 * to avoid discrepancies caused by server locality or daylight saving time changes.
 * </p>
 *
 * @author OnlineShop Team
 * @version 1.0
 */
public class TimeProvider {

    /**
     * The application's standard time zone (Coordinated Universal Time).
     */
    private static final ZoneId APP_ZONE = ZoneId.of("UTC");

    /**
     * Retrieves the current date and time in the application's standard time zone.
     *
     * @return The current {@link ZonedDateTime} in UTC.
     */
    public static ZonedDateTime now() {
        return ZonedDateTime.now(APP_ZONE);
    }
}