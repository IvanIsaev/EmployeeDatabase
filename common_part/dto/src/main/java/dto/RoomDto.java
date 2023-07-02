package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto
{
    private int id;

    @Override
    public String toString()
    {
        return new StringBuilder().append(String.format("%s = %d", "id", id)).toString();
    }
}
