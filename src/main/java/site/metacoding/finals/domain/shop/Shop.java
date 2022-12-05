package site.metacoding.finals.domain.shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.metacoding.finals.domain.AutoTime;
import site.metacoding.finals.domain.image_file.ImageFile;
import site.metacoding.finals.domain.user.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "shop")
@Entity
public class Shop extends AutoTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20, unique = true)
    private String shopName;
    @Column(nullable = false, length = 12, unique = true)
    private String phoneNumber;
    @Column(nullable = false, length = 30)
    private String address;
    @Column(length = 50)
    private String category;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String information;
    @Column(nullable = false, length = 10)
    private String openTime;
    @Column(nullable = false, length = 10)
    private String closeTime;

    @Column(nullable = false)
    private int perPrice;
    @Column(nullable = false)
    private int perHour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "shop", fetch = FetchType.LAZY)
    private ImageFile imageFile = new ImageFile(null, null, null, null, null);
    // null 방지 어케?

    @Column
    private boolean isOpened = Boolean.FALSE;

}
