import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@EnableConfigurationProperties(JwtUtils.class)
@ConfigurationProperties("jwt.config")
public class JwtUtils {
    /**
     * 签名私钥
     */
    private String key;
    
    /**
     * 签名的失效时间
     */
    private Long ttl;
    
    /**
     * 设置认证token
     *      id:登录用户id
     *      subject:登录用户名
     */
    public String createJwt(String id, String name, Map<String,Object> map){
        /**
         * 设置失效时间
         */
        long nowTime = System.currentTimeMillis();
        long exp = nowTime + ttl;
        /**
         * 创建jwtBuilder
         */
        JwtBuilder jwtBuilder = Jwts.builder().setId(id).setSubject(name)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key);
        /**
         * 根据map设置claims
         */
        for(Map.Entry<String,Object> entry:map.entrySet()){
            jwtBuilder.claim(entry.getKey(), entry.getValue());
        }
        jwtBuilder.setExpiration(new Date(exp));
        /**
         * 创建token
         */
        String token = jwtBuilder.compact();
        return token;
    }
    
    /**
     * 解析token字符串获取clamis
     */
    public Claims parseJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims;
    }
}
