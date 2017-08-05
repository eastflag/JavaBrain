package com.eastflag.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by eastflag on 2017-08-05.
 */
@Data
@Document(collection="cards")
public class CardsVO {
    @Id
    public long id;

    public String word;

    public List<ImageVO> images;
}

@Data
class ImageVO {
    private String src;
    private String youtube_src;
    private String thumbnail_src;
}