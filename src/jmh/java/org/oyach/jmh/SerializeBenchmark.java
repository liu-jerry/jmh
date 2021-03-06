package org.oyach.jmh;

import com.google.protobuf.InvalidProtocolBufferException;
import org.openjdk.jmh.annotations.*;
import org.oyach.jmh.ser.*;
import org.oyach.jmh.vo.TeacherProtos;

import java.util.concurrent.TimeUnit;

/**
 * @author liuzhenyuan
 * @version Last modified 15/4/9
 * @since 0.0.1
 */
@Fork(2) // 迭代集合次数
@Warmup(iterations = 4) // 预热迭代次数
@Measurement(iterations = 100) // 迭代次数
@BenchmarkMode(value = Mode.AverageTime)
@OutputTimeUnit(value = TimeUnit.MILLISECONDS)
@Threads(100)
public class SerializeBenchmark {


    @Benchmark
    public void javaSerBenchMark() {
        User user = new User();
        user.setId(3);
        user.setUsername("oyach");
        user.setNickename("欧阳澄泓");
        user.setLocked(false);

        byte[] bytes = JavaSerUtil.serialize(user);

        User user1 = (User) JavaSerUtil.deserialize(bytes); // 0.289
    }

    @Benchmark
    public void javaDesBenchMark() {

    }

    @Benchmark
    public void psSerBenchMark() {
        User user = new User();
        user.setId(3);
        user.setUsername("oyach");
        user.setNickename("欧阳澄泓");
        user.setLocked(false);

        byte[] bytes = PSUtil.obj2byte(user);


        User user1 = PSUtil.byte2obj(bytes, User.class); // 0.04
    }

    @Benchmark
    public void psDesBenchMark() {

    }

    @Benchmark
    public void jsonSerBenchMark() {
        User user = new User();
        user.setId(3);
        user.setUsername("oyach");
        user.setNickename("欧阳澄泓");
        user.setLocked(false);

        String json = JsonUtil.obj2json(user);

        User user1 = JsonUtil.json2Obj(json, User.class); //0.03
    }

    @Benchmark
    public void jsonDesBenchMark() {
        User user = new User();
        user.setId(3);
        user.setUsername("oyach");
        user.setNickename("欧阳澄泓");
        user.setLocked(false);

        String json = JsonUtil.obj2json2(user);

        User user1 = JsonUtil.json2Obj2(json, User.class); //0.037
    }

    @Benchmark
    public void kryoSerBenchMark() {
        KryoUtil util = new KryoUtil();
        User user = new User();
        user.setId(3);
        user.setUsername("oyach");
        user.setNickename("欧阳澄泓");
        user.setLocked(false);

        byte[] bytes = util.object2byte(user);

        User user1 = util.byte2object(bytes, User.class);//0.681
    }

    @Benchmark
    public void kryoDesBenchMark() {
        org.oyach.jmh.domain.User user = new org.oyach.jmh.domain.User();
        user.setId(3L);
        user.setUsername("oyach");
        user.setNickname("欧阳澄泓");

        byte[] bytes = ThriftUtil.obj2byte(user);


        org.oyach.jmh.domain.User2 newUser = ThriftUtil.byte2obj(bytes, org.oyach.jmh.domain.User2.class);
        // 0.022
    }


    @Benchmark
    public void kryoDesPB() {
        TeacherProtos.Teacher.Builder builder = TeacherProtos.Teacher.newBuilder();
        builder.setId(3L);
        builder.setUsername("oyach");
        builder.setNickename("欧阳澄泓");


        TeacherProtos.Teacher teacher = builder.build();

        byte[] bytes = teacher.toByteArray();

        try {
            TeacherProtos.Teacher teacher2 = TeacherProtos.Teacher.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        //0.010
    }

}
