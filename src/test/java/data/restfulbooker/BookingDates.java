package data.restfulbooker;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingDates {
    public String checkin;
    public String checkout;
}
